import { createInstance } from './index.js'

const instance = createInstance();

function createFeed(feed, success, fail) {
  instance
    .post('feed', feed)
    .then(success)
    .catch(fail);
}

export {
  createFeed,
}