# Satori - (SQA)

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Angular](https://img.shields.io/badge/Angular_19-DD0031?style=for-the-badge&logo=angular&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-131415?style=for-the-badge&logo=railway&logoColor=white)

Proyecto desarrollado con un enfoque centrado en el **Aseguramiento de la Calidad del Software (SQA)**. Implementa una arquitectura cliente-servidor (Frontend y Backend) orquestada mediante contenedores y validada a través de un pipeline automatizado de Integración y Despliegue Continuo (CI/CD).

---

## 🚀 Despliegue en Vivo

El proyecto se encuentra desplegado de forma automatizada en la nube de Railway (Hasta el 30/07/2026). Puedes acceder a la aplicación funcional a través del siguiente enlace:

🔗 **[Visitar Satori Web (Frontend)](https://satori-frontend-production.up.railway.app)**

*Nota: La API (Backend) también está expuesta públicamente y cuenta con documentación interactiva de Swagger para evaluar los endpoints.*

---

## 🛠️ Tecnologías y Arquitectura

Este repositorio (Monorepo) está dividido en dos microservicios principales:

### Frontend (SPA)
*   **Framework:** Angular 19
*   **Lenguaje:** TypeScript
*   **Servidor de Producción:** Nginx (Proxy Inverso y servidor de archivos estáticos)

### Backend (API RESTful)
*   **Framework:** Spring Boot
*   **Lenguaje:** Java 21
*   **Gestor de Dependencias:** Maven
*   **Persistencia:** Repositorios basados en archivos JSON persistentes (Volúmenes en la nube).
*   **Documentación API:** Swagger / OpenAPI

### Infraestructura (DevOps & SQA)
*   **Contenerización:** Docker (Múltiples `Dockerfile` optimizados por etapas).
*   **Integración Continua (CI):** GitHub Actions (Validación de pruebas unitarias).
*   **Despliegue Continuo (CD):** Railway PaaS.
*   **Testing:** JUnit y Mockito (Pruebas de partición de equivalencia y transición de estados).

---

## ⚙️ Cómo ejecutar el proyecto en local

Para correr este proyecto en tu propia máquina para fines de desarrollo o evaluación, sigue estos pasos:

### 1. Clonar el repositorio
```bash
git clone [https://github.com/TU-USUARIO/Satori-IntegracionContinua-SQA.git](https://github.com/TU-USUARIO/Satori-IntegracionContinua-SQA.git)
cd Satori-IntegracionContinua-SQA
```

### 2. Levantar los servicios con Docker

Asegúrate de tener Docker instalado y ejecutándose en tu sistema. Utilizaremos Docker Compose para la ejecución simultánea de ambos servicios.
En la raíz del proyecto, ejecuta el siguiente comando:

```bash
docker-compose up --build -d
```
### 3. Acceder a la aplicación

Una vez que los contenedores terminen de construirse y arrancar, los servicios estarán mapeados a tu red local:
*   **Frontend (Aplicación Web):** Estará disponible ingresando a http://localhost:4200.
*   **Backend (API & Swagger):** Estará disponible en http://localhost:8080.

Para detener la ejecución de la aplicación de manera limpia, simplemente ejecuta:
```bash
docker-compose down
```