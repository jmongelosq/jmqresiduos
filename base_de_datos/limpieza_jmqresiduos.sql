-- =============================================================
-- Limpieza de la base jmqresiduos
-- Elimina tablas y vistas que el sistema ya no utiliza:
--   - Puntos limpios: punto_limpio, ingreso_punto_limpio,
--     recepcion_punto_limpio
--   - asignacion_personal
--   - cobro (y la columna pago.id_cobro que la referenciaba)
--   - Vistas: vw_clientes, vw_personal,
--     vw_estado_cuenta_clientes, vw_estado_cuenta_clientes_v2
--
-- ANTES DE EJECUTAR: hacer un respaldo de la base.
-- Se ejecuta todo en una transacción: si algo falla, no se borra nada.
-- No se usa CASCADE a propósito, para que PostgreSQL avise si existe
-- alguna dependencia que no esté contemplada.
-- =============================================================

BEGIN;

-- Muestra cuántos registros se van a perder (aparece en Mensajes / Messages)
DO $$
DECLARE
    n bigint;
BEGIN
    SELECT count(*) INTO n FROM public.punto_limpio;            RAISE NOTICE 'punto_limpio: % filas', n;
    SELECT count(*) INTO n FROM public.ingreso_punto_limpio;    RAISE NOTICE 'ingreso_punto_limpio: % filas', n;
    SELECT count(*) INTO n FROM public.recepcion_punto_limpio;  RAISE NOTICE 'recepcion_punto_limpio: % filas', n;
    SELECT count(*) INTO n FROM public.asignacion_personal;     RAISE NOTICE 'asignacion_personal: % filas', n;
    SELECT count(*) INTO n FROM public.cobro;                   RAISE NOTICE 'cobro: % filas', n;
    SELECT count(*) INTO n FROM public.pago WHERE id_cobro IS NOT NULL;
    RAISE NOTICE 'pagos vinculados a un cobro: %', n;
END $$;

-- 1. Vistas sin uso
DROP VIEW IF EXISTS public.vw_estado_cuenta_clientes_v2;
DROP VIEW IF EXISTS public.vw_estado_cuenta_clientes;
DROP VIEW IF EXISTS public.vw_clientes;
DROP VIEW IF EXISTS public.vw_personal;

-- 2. Quitar la relación pago -> cobro y luego la tabla cobro
ALTER TABLE public.pago DROP CONSTRAINT IF EXISTS fk_pago_cobro;
DROP INDEX IF EXISTS public.idx_pago_id_cobro;
ALTER TABLE public.pago DROP COLUMN IF EXISTS id_cobro;
DROP TABLE IF EXISTS public.cobro;

-- 3. Puntos limpios (primero las tablas hijas)
DROP TABLE IF EXISTS public.ingreso_punto_limpio;
DROP TABLE IF EXISTS public.recepcion_punto_limpio;
DROP TABLE IF EXISTS public.punto_limpio;

-- 4. Personal por asignación
DROP TABLE IF EXISTS public.asignacion_personal;

COMMIT;

-- Verificación: debe devolver 0 filas
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN ('punto_limpio', 'ingreso_punto_limpio', 'recepcion_punto_limpio',
                     'asignacion_personal', 'cobro', 'vw_clientes', 'vw_personal',
                     'vw_estado_cuenta_clientes', 'vw_estado_cuenta_clientes_v2');
