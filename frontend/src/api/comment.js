import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();


// 댓글 crud

// r
function commentList(id, num, success, fail) {
  instance
    .get(`feeds/${id}/comments`, {
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
    .post(`feeds/${id}/comments`, comment)
    .then(success)
    .catch(fail);
}

// 멘션
function searchAutoUser(key, success, fail) {
  instance
    .get(`search/auto/user`, {
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
    .put(`feeds/${id}/comments/${commentId}`, comment)
    .then(success)
    .catch(fail);
}

// d
function deleteComment(id, commentId, success, fail) {
  instance
    .delete(`feeds/${id}/comments/${commentId}`)
    .then(success)
    .catch(fail);
}

// 박수
function clapComment(commentId, success, fail) {
  instance
    .post(`feeds/comments/${commentId}/claps`)
    .then(success)
    .catch(fail);
}

function clapCommentList(commentId, success, fail) {
  instance
    .get(`feeds/comments/${commentId}/claps`)
    .then(success)
    .catch(fail);
}

export {
  commentList,
  createComment,
  updateComment,
  deleteComment,
  clapComment,
  clapCommentList,
  searchAutoUser
}