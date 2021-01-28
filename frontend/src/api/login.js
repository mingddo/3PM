import { createInstance } from './index.js'

const instance = createInstance();

function getUser(userId, success, fail) {
  instance
    .post('user/kakao', userId)
    .then(success)
    .catch(fail);
}

export {
  getUser,
}