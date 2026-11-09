<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("usuario") != null) {
        response.sendRedirect("dashboard.jsp");
        return;
    }

    Object error = request.getAttribute("error");
    String usuarioIngresado = request.getParameter("usuario");
    if (usuarioIngresado == null) {
        usuarioIngresado = "";
    }
    usuarioIngresado = usuarioIngresado
            .replace("&", "&amp;")
            .replace("\"", "&quot;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Iniciar sesión | JMQ SYSTEM</title>
  <link rel="icon" type="image/x-icon" href="favicon.ico">
  <link rel="stylesheet" href="plugins/fontawesome-free/css/all.min.css">
  <style>
    :root {
      --azul-oscuro: #0f2a4f;
      --azul: #1f4f9c;
      --azul-claro: #2e75d6;
      --texto: #1c2635;
      --gris: #6b7789;
      --borde: #d9e1ec;
      --fondo: #eef2f7;
      --error: #b42318;
    }

    * {
      box-sizing: border-box;
    }

    html, body {
      height: 100%;
      margin: 0;
    }

    body {
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
      background: var(--fondo);
      color: var(--texto);
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 24px;
    }

    .acceso {
      width: 100%;
      max-width: 980px;
      min-height: 560px;
      display: grid;
      grid-template-columns: 1.05fr 1fr;
      background: #fff;
      border-radius: 20px;
      overflow: hidden;
      box-shadow: 0 24px 60px rgba(15, 42, 79, 0.18);
    }

    .panel {
      position: relative;
      padding: 44px 44px 36px;
      color: #fff;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      background:
        linear-gradient(160deg, rgba(15, 42, 79, 0.92) 0%, rgba(31, 79, 156, 0.78) 55%, rgba(46, 117, 214, 0.62) 100%),
        url("dist/img/fondo_login.jpg") center / cover no-repeat;
    }

    .marca {
      display: flex;
      align-items: center;
      gap: 14px;
    }

    .marca .logo-chico {
      width: 52px;
      height: 52px;
      border-radius: 14px;
      background: #fff;
      padding: 6px;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .marca .logo-chico img {
      max-width: 100%;
      max-height: 100%;
    }

    .marca span {
      font-size: 20px;
      font-weight: 800;
      letter-spacing: 1.5px;
    }

    .panel-centro {
      margin: 30px 0;
    }

    .logo-grande {
      width: 132px;
      height: 132px;
      border-radius: 28px;
      background: #fff;
      padding: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: 0 16px 36px rgba(0, 0, 0, 0.25);
      margin-bottom: 26px;
    }

    .logo-grande img {
      max-width: 100%;
      max-height: 100%;
    }

    .panel h1 {
      margin: 0 0 12px;
      font-size: 34px;
      font-weight: 800;
      line-height: 1.15;
    }

    .panel p {
      margin: 0;
      font-size: 15px;
      line-height: 1.6;
      color: #d6e2f3;
      max-width: 380px;
    }

    .modulos {
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
      margin-top: 22px;
    }

    .modulos span {
      font-size: 12px;
      font-weight: 600;
      padding: 6px 12px;
      border-radius: 30px;
      background: rgba(255, 255, 255, 0.14);
      border: 1px solid rgba(255, 255, 255, 0.25);
    }

    .modulos i {
      margin-right: 5px;
    }

    .panel-pie {
      font-size: 12px;
      color: #b8c8e0;
    }

    .formulario {
      padding: 56px 52px;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .formulario h2 {
      margin: 0 0 6px;
      font-size: 28px;
      font-weight: 800;
      color: var(--azul-oscuro);
    }

    .formulario .subtitulo {
      margin: 0 0 30px;
      color: var(--gris);
      font-size: 14px;
    }

    .campo {
      margin-bottom: 18px;
    }

    .campo label {
      display: block;
      font-size: 13px;
      font-weight: 700;
      margin-bottom: 7px;
      color: var(--texto);
    }

    .entrada {
      position: relative;
    }

    .entrada > i {
      position: absolute;
      left: 15px;
      top: 50%;
      transform: translateY(-50%);
      color: var(--gris);
      font-size: 15px;
    }

    .entrada input {
      width: 100%;
      height: 48px;
      border: 1px solid var(--borde);
      border-radius: 12px;
      padding: 0 46px 0 44px;
      font-size: 15px;
      color: var(--texto);
      background: #f8fafc;
      transition: border-color .15s, box-shadow .15s, background .15s;
    }

    .entrada input:focus {
      outline: none;
      border-color: var(--azul-claro);
      background: #fff;
      box-shadow: 0 0 0 4px rgba(46, 117, 214, 0.15);
    }

    .ver-clave {
      position: absolute;
      right: 6px;
      top: 50%;
      transform: translateY(-50%);
      width: 38px;
      height: 38px;
      border: 0;
      border-radius: 10px;
      background: transparent;
      color: var(--gris);
      cursor: pointer;
      font-size: 15px;
    }

    .ver-clave:hover {
      background: #eef2f7;
      color: var(--azul);
    }

    .error {
      display: flex;
      gap: 10px;
      align-items: flex-start;
      padding: 12px 14px;
      margin-bottom: 18px;
      border-radius: 12px;
      background: #fdecea;
      border: 1px solid #f5c2bd;
      color: var(--error);
      font-size: 13px;
      line-height: 1.45;
    }

    .boton {
      width: 100%;
      height: 50px;
      margin-top: 8px;
      border: 0;
      border-radius: 12px;
      background: linear-gradient(135deg, var(--azul) 0%, var(--azul-claro) 100%);
      color: #fff;
      font-size: 16px;
      font-weight: 700;
      letter-spacing: .3px;
      cursor: pointer;
      box-shadow: 0 10px 22px rgba(31, 79, 156, 0.30);
      transition: transform .15s, box-shadow .15s;
    }

    .boton:hover {
      transform: translateY(-1px);
      box-shadow: 0 14px 26px rgba(31, 79, 156, 0.36);
    }

    .boton:disabled {
      opacity: .75;
      cursor: wait;
      transform: none;
    }

    .boton i {
      margin-right: 8px;
    }

    .nota {
      margin-top: 26px;
      text-align: center;
      font-size: 12px;
      color: var(--gris);
    }

    .nota i {
      color: var(--azul-claro);
      margin-right: 4px;
    }

    @media (max-width: 820px) {
      body {
        padding: 0;
        align-items: stretch;
      }

      .acceso {
        grid-template-columns: 1fr;
        min-height: 100vh;
        border-radius: 0;
        box-shadow: none;
      }

      .panel {
        padding: 26px 24px;
      }

      .panel-centro {
        margin: 18px 0 0;
        display: flex;
        align-items: center;
        gap: 18px;
      }

      .logo-grande {
        width: 82px;
        height: 82px;
        border-radius: 20px;
        padding: 9px;
        margin-bottom: 0;
        flex: none;
      }

      .panel h1 {
        font-size: 24px;
        margin-bottom: 6px;
      }

      .panel p {
        font-size: 13px;
      }

      .marca, .modulos, .panel-pie {
        display: none;
      }

      .formulario {
        padding: 30px 24px 40px;
        justify-content: flex-start;
      }
    }
  </style>
</head>
<body>

<main class="acceso">

  <section class="panel">
    <div class="marca">
      <span>JMQ SYSTEM</span>
    </div>

    <div class="panel-centro">
      <div class="logo-grande"><img src="dist/img/jmmqlogo.png" alt="Logo JMQ SYSTEM"></div>
      <div>
        <h1>¡Bienvenido de nuevo!</h1>
        <p>Gestión del servicio de recolección de residuos de la ciudad de Villa Elisa: clientes, recolecciones, facturación, cobros e informes en un solo lugar.</p>
        <div class="modulos">
          <span><i class="fas fa-recycle"></i>Recolección</span>
          <span><i class="fas fa-file-invoice-dollar"></i>Facturación</span>
          <span><i class="fas fa-hand-holding-usd"></i>Cobros</span>
          <span><i class="fas fa-chart-bar"></i>Informes</span>
        </div>
      </div>
    </div>

    <div class="panel-pie">Villa Elisa · Paraguay</div>
  </section>

  <section class="formulario">
    <h2>Iniciar sesión</h2>
    <p class="subtitulo">Ingresá con tu usuario y contraseña para continuar.</p>

    <% if (error != null) { %>
      <div class="error">
        <i class="fas fa-exclamation-circle"></i>
        <span><%= error %></span>
      </div>
    <% } %>

    <form action="LoginController" method="post" id="formLogin" autocomplete="on">
      <div class="campo">
        <label for="usuario">Usuario</label>
        <div class="entrada">
          <i class="fas fa-user"></i>
          <input type="text" id="usuario" name="usuario" placeholder="Ingresá tu usuario"
                 value="<%= usuarioIngresado %>" autocomplete="username" required autofocus>
        </div>
      </div>

      <div class="campo">
        <label for="clave">Contraseña</label>
        <div class="entrada">
          <i class="fas fa-lock"></i>
          <input type="password" id="clave" name="clave" placeholder="Ingresá tu contraseña"
                 autocomplete="current-password" required>
          <button type="button" class="ver-clave" id="verClave" title="Mostrar contraseña"
                  aria-label="Mostrar contraseña">
            <i class="fas fa-eye"></i>
          </button>
        </div>
      </div>

      <button type="submit" class="boton" id="botonIngresar">
        <i class="fas fa-sign-in-alt"></i>Ingresar
      </button>
    </form>

    <div class="nota"><i class="fas fa-shield-alt"></i>Acceso exclusivo para personal autorizado</div>
  </section>

</main>

<script>
  (function () {
    var clave = document.getElementById("clave");
    var boton = document.getElementById("verClave");
    var icono = boton.querySelector("i");

    boton.addEventListener("click", function () {
      var oculta = clave.type === "password";
      clave.type = oculta ? "text" : "password";
      icono.className = oculta ? "fas fa-eye-slash" : "fas fa-eye";
      boton.title = oculta ? "Ocultar contraseña" : "Mostrar contraseña";
      clave.focus();
    });

    document.getElementById("formLogin").addEventListener("submit", function () {
      var ingresar = document.getElementById("botonIngresar");
      ingresar.disabled = true;
      ingresar.innerHTML = '<i class="fas fa-spinner fa-spin"></i>Ingresando...';
    });

    if (document.getElementById("usuario").value) {
      clave.focus();
    }

    window.addEventListener("pageshow", function (e) {
      if (e.persisted) {
        location.reload();
      }
    });
  })();
</script>

</body>
</html>
