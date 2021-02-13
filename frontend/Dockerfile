FROM node:alpine as builder
WORKDIR /app
COPY ./package.json ./

RUN apt-get update && apt-get -y dist-upgrade 
RUN apt-get -y install node-gyp build-essential libssl-dev libffi-dev libblas3 libc6 liblapack3 gcc python3-dev 
RUN apt install -y netcat

# RUN npm install -g npm@7.5.4
# RUN npm install -g pm2 node-gyp
# RUN npm install -g node-sass
# RUN npm install -g sass
# RUN npm install -g sass-loader
RUN npm install --force
COPY ./ ./
RUN npm run build

FROM nginx
EXPOSE 3000
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist /usr/share/nginx/html