FROM node:alpine as builder
WORKDIR /app
COPY ./package.json ./

RUN apk add g++ make python
RUN npm install --force
COPY ./ ./
RUN npm run build

FROM nginx
EXPOSE 3000
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html