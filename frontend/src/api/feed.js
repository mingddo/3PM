import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

// 피드 crud

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

// 피드 좋아요
function clapFeed(pk, success, fail) {
  instance
    .post(`feeds/${pk}/claps`)
    .then(success)
    .catch(fail);
}

// 피드 좋아요 리스트
function clapFeedList(pk, success, fail) {
  instance
    .get(`feeds/${pk}/claps`)
    .then(success)
    .catch(fail);
}

export {
  clapFeed,
  clapFeedList,
  searchAutoTag
}