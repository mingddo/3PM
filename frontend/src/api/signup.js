import { createInstance } from './index.js'

const instance = createInstance();

function createUser(user, success, fail) {
  instance
    .post('user/kakao2', user)
    .then(success)
    .catch(fail);
}

function checkOverlapped(username, success, fail) {
  instance
    .post('user/dupl',username)
    .then(success)
    .catch(fail);
    
}
export {
  createUser,
  checkOverlapped,
}