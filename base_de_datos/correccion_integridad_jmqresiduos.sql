-- =============================================================
-- Corrección de integridad de la base jmqresiduos
--
--   1. Elimina las recolecciones que apuntan a asignaciones que no existen
--      (recolecciones "huérfanas"; no aparecen en el sistema ni en los reportes).
--   2. Agrega la clave foránea recoleccion.id_asignacion -> asignacion.
--   3. Quita la restricción repetida de pago (chk_pago_monto_positivo hace
--      lo mismo que chk_pago_monto: monto > 0).
--
-- ANTES DE EJECUTAR: hacer un respaldo de la base.
-- Se ejecuta todo en una transacción: si algo falla, no se cambia nada.
-- =============================================================

BEGIN;

-- 1. Recolecciones huérfanas
DO $$
DECLARE
    r RECORD;
    total INTEGER := 0;
BEGIN
    FOR r IN
        SELECT rc.id_recoleccion, rc.id_asignacion, rc.fecha,
               (SELECT count(*) FROM public.clasificacion_residuo c
                 WHERE c.id_recoleccion = rc.id_recoleccion) AS clasificaciones
        FROM public.recoleccion rc
        LEFT JOIN public.asignacion a ON a.id_asignacion = rc.id_asignacion
        WHERE a.id_asignacion IS NULL
        ORDER BY rc.id_recoleccion
    LOOP
        -- Por seguridad: si una huérfana tiene clasificaciones, no se borra nada
        IF r.clasificaciones > 0 THEN
            RAISE EXCEPTION 'La recolección % (asignación inexistente %) tiene % clasificaciones. No se modificó nada.',
                r.id_recoleccion, r.id_asignacion, r.clasificaciones;
        END IF;

        RAISE NOTICE 'Se elimina la recolección % (asignación inexistente %, fecha %)',
            r.id_recoleccion, r.id_asignacion, r.fecha;
        total := total + 1;
    END LOOP;

    RAISE NOTICE 'Recolecciones huérfanas a eliminar: %', total;
END $$;

DELETE FROM public.recoleccion rc
WHERE NOT EXISTS (
    SELECT 1 FROM public.asignacion a WHERE a.id_asignacion = rc.id_asignacion
);

-- 2. Clave foránea recoleccion -> asignacion
--    (no permite borrar una asignación que tiene recolecciones)
ALTER TABLE public.recoleccion
    ADD CONSTRAINT fk_recoleccion_asignacion
    FOREIGN KEY (id_asignacion)
    REFERENCES public.asignacion (id_asignacion)
    ON UPDATE CASCADE
    ON DELETE RESTRICT;

-- 3. Restricción repetida en pago
ALTER TABLE public.pago DROP CONSTRAINT IF EXISTS chk_pago_monto_positivo;

COMMIT;

-- Verificación
SELECT 'Recolecciones sin asignación' AS control, count(*)::text AS resultado
FROM public.recoleccion rc
LEFT JOIN public.asignacion a ON a.id_asignacion = rc.id_asignacion
WHERE a.id_asignacion IS NULL
UNION ALL
SELECT 'Clave foránea recoleccion -> asignacion',
       CASE WHEN EXISTS (SELECT 1 FROM pg_constraint
                         WHERE conname = 'fk_recoleccion_asignacion') THEN 'creada' ELSE 'FALTA' END
UNION ALL
SELECT 'Restricciones de monto en pago',
       (SELECT string_agg(conname, ', ') FROM pg_constraint
         WHERE conrelid = 'public.pago'::regclass AND conname LIKE 'chk_pago_monto%');
