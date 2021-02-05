import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

function createFeed(feed, success, fail) {
  instance
    .post('indoor', feed)
    .then(success)
    .catch(fail);
}

function readFeed(feed_pk, success, fail) {
  instance
    .get(`indoor/${feed_pk}`)
    .then(success)
    .catch(fail);
}

function updateFeed(feed_pk, feed, success, fail) {
  instance
    .put(`indoor/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

function deleteFeed(feed_pk, success, fail) {
  instance
  .delete(`indoor/${feed_pk}`)
  .then(success)
  .catch(fail);
}

function feedList(list_pk, success, fail) {
  instance
  .get(`indoor/list/${list_pk}`)
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