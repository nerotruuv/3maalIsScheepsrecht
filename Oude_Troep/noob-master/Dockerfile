FROM node:lts-alpine

WORKDIR /app

COPY package.json yarn.lock ./
RUN yarn

COPY . .

EXPOSE 5443

CMD ["yarn", "dev-server"]
