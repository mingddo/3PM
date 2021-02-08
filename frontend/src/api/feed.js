import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function createFeed(feed, success, fail) {
  instance
    .post('indoors', feed)
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

export {
  createFeed,
  readFeed,
  updateFeed,
  deleteFeed,
  feedList
}