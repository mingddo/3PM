import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function createOutdoors(feed, success, fail) {
  instance
    .post('outdoors', feed)
    .then(success)
    .catch(fail);
}

function uploadOutdoorsFile(id, file, success, fail) {
  instance
    .post(`outdoors/${id}`, file)
    .then(success)
    .catch(fail);
}

function readOutdoors(feed_pk, success, fail) {
  instance
    .get(`outdoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}

function outdoorsList(pk, success, fail) {
  instance
  .get(`outdoors`, {
    params: {
      startNum: pk
    }
  })
  .then(success)
  .catch(fail);
}

// u
function updateOutdoors(feed_pk, feed, success, fail) {
  instance
    .put(`outdoors/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

// d
function deleteOutdoors(feed_pk, success, fail) {
  instance
    .delete(`outdoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}

export {
  createOutdoors,
  uploadOutdoorsFile,
  readOutdoors,
  outdoorsList,
  updateOutdoors,
  deleteOutdoors
}