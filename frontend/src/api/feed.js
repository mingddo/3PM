import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

// 피드 crud

// c
function createFeed(feed, success, fail) {
  instance
    .post('indoors', feed)
    .then(success)
    .catch(fail);
}

// 파일만
function uploadFile(id, file, success, fail) {
  instance
    .post(`indoors/${id}`, file)
    .then(success)
    .catch(fail);
}

// 태그 자동완성
function searchAutoTag(key, success, fail) {
  instance
    .get(`search/auto/tag`, {
      params: {
        hashtag: key,
      }
    })
    .then(success)
    .catch(fail);
}

//r

function readFeed(feed_pk, success, fail) {
  instance
    .get(`indoors/${feed_pk}`)
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

// u
function updateFeed(feed_pk, feed, success, fail) {
  instance
    .put(`indoors/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

// d
function deleteFeed(feed_pk, success, fail) {
  instance
    .delete(`indoors/${feed_pk}`)
    .then(success)
    .catch(fail);
}


// 피드 좋아요
function clapFeed(pk, success, fail) {
  instance
    .post(`indoors/${pk}/claps`)
    .then(success)
    .catch(fail);
}

// 피드 좋아요 리스트
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
  clapFeedList,
  searchAutoTag
}