# stage 1

FROM node:alpine AS build

WORKDIR /projSpecificWeb

COPY . /projSpecificWeb

RUN npm install && npm run build

# stage 2

FROM nginx:alpine

COPY --from=build /projSpecificWeb/dist/proj-engine-web /usr/share/nginx/html

EXPOSE 4200