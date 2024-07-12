# Pull official base image
FROM node:18.12.1-alpine

# Add a bash shell to the image
RUN apk add --no-cache bash

# Set working directory
WORKDIR /app

# Add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# Install app dependencies
COPY package.json ./
RUN yarn install --silent

# Add all files to the app directory
COPY . ./

# Start the app
ENTRYPOINT ["yarn", "start"]