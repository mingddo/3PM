import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function createFeed(feed, success, fail) {
  instance
    .post('indoors', feed)
    .then(success)
    .catch(fail);
}

function uploadFile(id, file, success, fail) {
  instance
    .post(`indoors/${id}`, {}, {
      params: {
        files: file,
      }
    })
    .then(success)
    .catch(fail);
}

function readFeed(feed_pk, success, fail) {
  instance
    .get(`indoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}

function updateFeed(feed_pk, feed, success, fail) {
  instance
    .put(`indoors/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

function deleteFeed(feed_pk, success, fail) {
  instance
    .delete(`indoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}

function feedList(pk, success, fail) {
  instance
  .get(`indoors`, {
    params: {
      startNum: pk
    }
  })
  .then(success)
  .catch(fail);
}

function clapFeed(pk, success, fail) {
  instance
    .post(`indoors/${pk}/claps`)
    .then(success)
    .catch(fail);
}

function clapFeedList(pk, success, fail) {
  instance
    .get(`indoors/${pk}/claps`)
    .then(success)
    .catch(fail);
}

export {
  createFeed,
  uploadFile,
  readFeed,
  updateFeed,
  deleteFeed,
  feedList,
  clapFeed,
  clapFeedList
}