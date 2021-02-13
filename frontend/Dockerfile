FROM node:alpine as builder
WORKDIR /app
COPY ./package.json ./
RUN npm install -g npm@7.5.4
RUN npm install -g pm2 node-gyp
RUN npm install -g node-sass
RUN npm install -g sass
RUN npm install -g sass-loader
RUN npm install --force
COPY ./ ./
RUN npm run build

FROM nginx
EXPOSE 3000
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html