import { createInstance } from './index.js'

const instance = createInstance();

function createUser(user, success, fail) {
  instance
    .post('user', user)
    .then(success)
    .catch(fail);
}

export {
  createUser,
}