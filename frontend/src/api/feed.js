import { createInstance } from './index.js'

const instance = createInstance();

function createFeed(feed, success, fail) {
  instance
    .post('feed', feed)
    .then(success)
    .catch(fail);
}

function readFeed(feed_pk, success, fail) {
  instance
    .get(`feed/${feed_pk}`)
    .then(success)
    .catch(fail);
}

function updateFeed(feed_pk, feed, success, fail) {
  instance
    .put(`feed/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

function deleteFeed(feed_pk, success, fail) {
  instance
  .delete(`feed/${feed_pk}`)
  .then(success)
  .catch(fail);
}

function feedList(list_pk, success, fail) {
  instance
  .get(`feed/list/${list_pk}`)
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