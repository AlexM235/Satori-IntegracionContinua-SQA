# ETAPA 1: Construcción (Node.js 20 para compilar Angular 19)
FROM node:20 AS build
WORKDIR /app

# Copiamos package.json e instalamos dependencias
COPY package*.json ./
RUN npm install

# Copiamos el resto del código y compilamos para producción
COPY . .
RUN npm run build --configuration=production

# ETAPA 2: Ejecución (Nginx para servir la web)
FROM nginx:alpine

# Copiamos los archivos compilados a la carpeta pública de Nginx
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copiamos la configuración de Nginx
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponemos el puerto web
EXPOSE 80

# Arrancamos Nginx
CMD ["nginx", "-g", "daemon off;"]