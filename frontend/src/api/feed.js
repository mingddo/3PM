import { createInstance } from './index.js'

const instance = createInstance();

function getFeed(success, fail) {
  instance
    .get('corona/')
    .then(success)
    .catch(fail);
}

export {
  getFeed,
}