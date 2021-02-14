import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();
let token = null
if (localStorage.getItem('vuex') != null ) {
  let vuex_data = localStorage.getItem('vuex')
  vuex_data = JSON.parse(vuex_data);
  token = vuex_data["authToken"]
}
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
    },
    headers: {
      "Authorization" : token
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
  readFeed,
  updateFeed,
  deleteFeed,
  feedList,
  clapFeed,
  clapFeedList
}