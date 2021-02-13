import { createInstance } from './index.js'

const instance = createInstance();

function getUser(userId, success, fail) {
  instance
    .post('users/login', userId)
    .then(success)
    .catch(fail);
}

export {
  getUser,
}