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

function outdoorReco(success, fail) {
  instance
    .get(`outdoors/recommend`)
    .then(success)
    .catch(fail);
}

function groupFeedReco(success, fail) {
  instance
    .get(`groups/feeds/recommend`)
    .then(success)
    .catch(fail);
}
export {
  indoorReco,
  outdoorReco,
  groupFeedReco
}