import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function indoorReco(success, fail) {
  instance
    .get(`indoors/recommend`, {
      params: {
        startNum: 0
      }
    })
    .then(success)
    .catch(fail);
}

export {
  indoorReco,
}