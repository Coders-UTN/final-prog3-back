# Final-Prog3 - Conexión Backend y Frontend

## 1. Configurar y Ejecutar el Backend (Spring Boot)

El backend debe estar corriendo para que el frontend pueda hacer peticiones.

1.  **Configurar la Base de Datos:**
    * Asegúrate de que tu archivo `src/main/resources/application.properties` esté configurado para tu base de datos (se recomienda **H2 en memoria** para pruebas rápidas).
2.  **Configurar CORS:**
    * Verifica que exista la clase `WebConfig.java` y que esté permitiendo el origen del frontend (ej. `http://localhost:5173`).
3.  **Ejecutar:**
    * Inicia la aplicación Spring Boot desde tu IDE (ej. ejecutando el método `main` en `FoodstoreApplication.java`).
4.  **Verificar:**
    * El backend debe estar corriendo en `http://localhost:8080`.

---

## 2. Configurar y Ejecutar el Frontend (Vite)

El frontend necesita instalar sus dependencias y ejecutarse en modo de desarrollo.

1.  **Abrir Terminal:**
    * Navega a la carpeta raíz de tu proyecto frontend.
2.  **Instalar Dependencias:**
    * Si es la primera vez, ejecuta `npm install`.
3.  **Ejecutar:**
    * Inicia el servidor de desarrollo de Vite:
        ```bash
        npm run dev
        ```
4.  **Verificar:**
    * El frontend debe estar corriendo en `http://localhost:5173`.

---

## 3. Guía de Pruebas de Conexión

Una vez que ambos servidores estén corriendo, sigue estos pasos para probar el flujo de autenticación completo.

### Prueba A: Registro de Usuario (Frontend)

1.  **Abrir Página:**
    * Ve a la página de registro en tu navegador:
    * `http://localhost:5173/src/pages/auth/register/register.html`
2.  **Abrir Herramientas de Desarrollador:**
    * En Mac: `Cmd + Option + I`
    * En Windows: `F12`
    * Ve a la pestaña **`Network` (Red)**.
3.  **Probar:**
    * Rellena **todos** los campos del formulario con datos de un usuario nuevo (usa un email que no exista).
    * Haz clic en "Registrarse".
4.  **Verificación (Éxito):**
    * ✅ La página **no debe recargarse**.
    * ✅ Debe aparecer el mensaje de éxito (ej. "¡Registro exitoso!...") en color verde.
    * ✅ En `Network`, debe aparecer una petición `register` con **Status `201 Created`**.
    * ✅ Serás redirigido al `home.html` después de unos segundos.

### Prueba B: Falla de Registro (Email duplicado)

1.  **Probar:**
    * Vuelve a la página de registro e intenta registrarte usando **exactamente el mismo email** de la Prueba A.
2.  **Verificación (Falla):**
    * ✅ La página **no debe recargarse**.
    * ✅ Debe aparecer un mensaje de error (ej. "Error al registrarse.") en color rojo.
    * ✅ En `Network`, debe aparecer una petición `register` con **Status `4xx` o `5xx` (rojo)**.

### Prueba C: Inicio de Sesión (Login)

1.  **Abrir Página:**
    * Ve a la página de inicio de sesión:
    * `http://localhost:5173/src/pages/auth/login/login.html`
2.  **Probar:**
    * Ingresa las credenciales (email y password) del usuario que creaste en la **Prueba A**.
    * Haz clic en "Iniciar Sesión".
3.  **Verificación (Éxito):**
    * ✅ La página **no debe recargarse**.
    * ✅ Debe aparecer el mensaje de bienvenida (ej. "¡Bienvenido, ...!").
    * ✅ En `Network`, debe aparecer una petición `login` con **Status `200 OK`**.
    * ✅ En `Application` > `Local Storage`, debe guardarse la clave `food_store_user`.
    * ✅ Serás redirigido al `home.html`.

### Prueba D: Falla de Login (Contraseña incorrecta)

1.  **Probar:**
    * Vuelve a la página de login. Ingresa el email correcto pero una **contraseña incorrecta**.
    * Haz clic en "Iniciar Sesión".
2.  **Verificación (Falla):**
    * ✅ La página **no debe recargarse** (la URL no debe cambiar con `?email=...`).
    * ✅ Debe aparecer el mensaje de error ("Email o contraseña inválidos") en rojo.
    * ✅ En `Network`, debe aparecer `login` con **Status `401 Unauthorized`**.
