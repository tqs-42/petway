# stage 1

FROM node:alpine AS build

WORKDIR /projEngineWeb

COPY . /projEngineWeb

RUN npm install && npm run build

# stage 2

# FROM nginx:alpine

# COPY --from=build /projEngineWeb/dist/proj-engine-web /usr/share/nginx/html

# EXPOSE 4200