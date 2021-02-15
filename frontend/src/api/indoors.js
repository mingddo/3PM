import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

// 피드 crud

// c
function createIndoors(feed, success, fail) {
  instance
    .post('indoors', feed)
    .then(success)
    .catch(fail);
}

// 파일만
function uploadIndoorsFile(id, file, success, fail) {
  instance
    .post(`indoors/${id}`, file)
    .then(success)
    .catch(fail);
}

//r

function readIndoors(feed_pk, success, fail) {
  instance
    .get(`indoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}

function indoorsList(pk, success, fail) {
  instance
  .get(`indoors`, {
    params: {
      startNum: pk
    }
  })
  .then(success)
  .catch(fail);
}

// u
function updateIndoors(feed_pk, feed, success, fail) {
  instance
    .put(`indoors/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

// d
function deleteIndoors(feed_pk, success, fail) {
  instance
    .delete(`indoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}

export {
  createIndoors,
  uploadIndoorsFile,
  readIndoors,
  indoorsList,
  updateIndoors,
  deleteIndoors
}