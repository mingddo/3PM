import { createInstance } from './index.js'

const instance = createInstance();

function createUser(user, success, fail) {
  instance
    .post('users/join', user)
    .then(success)
    .catch(fail);
}

function checkOverlapped(username, success, fail) {
  instance
    .post('users/dupl',username)
    .then(success)
    .catch(fail);
    
}
export {
  createUser,
  checkOverlapped,
}