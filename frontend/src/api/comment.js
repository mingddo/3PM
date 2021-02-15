import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();


// 댓글 crud

// r
function commentList(id, num, success, fail) {
  instance
    .get(`indoors/${id}/comments`, {
      params: {
        startNum: num
      }
    })
    .then(success)
    .catch(fail);
}

// c
function createComment(id, comment, success, fail) {
  instance
    .post(`indoors/${id}/comments`, comment)
    .then(success)
    .catch(fail);
}

// 멘션
function searchAutoUser(key, success, fail) {
  instance
    .get(`search/auto/tag`, {
      params: {
        nickname: key,
      }
    })
    .then(success)
    .catch(fail)
}

// u
function updateComment(id, commentId, comment, success, fail) {
  instance
    .put(`indoors/${id}/comments/${commentId}`, comment)
    .then(success)
    .catch(fail);
}

// d
function deleteComment(id, commentId, success, fail) {
  instance
    .delete(`indoors/${id}/comments/${commentId}`)
    .then(success)
    .catch(fail);
}

// 박수
function clapComment(commentId, success, fail) {
  instance
    .post(`indoors/comments/${commentId}/claps`)
    .then(success)
    .catch(fail);
}

function clapList(commentId, success, fail) {
  instance
    .get(`indoors/comments/${commentId}/claps`)
    .then(success)
    .catch(fail);
}

export {
  commentList,
  createComment,
  updateComment,
  deleteComment,
  clapComment,
  clapList,
  searchAutoUser
}