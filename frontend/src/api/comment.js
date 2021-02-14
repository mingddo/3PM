import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

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

function createComment(id, comment, success, fail) {
  instance
    .post(`indoors/${id}/comments`, comment)
    .then(success)
    .catch(fail);
}

function updateComment(id, commentId, comment, success, fail) {
  instance
    .put(`indoors/${id}/comments/${commentId}`, comment)
    .then(success)
    .catch(fail);
}

function deleteComment(id, commentId, success, fail) {
  instance
    .delete(`indoors/${id}/comments/${commentId}`)
    .then(success)
    .catch(fail);
}

function clapComment(commentId, success, fail) {
  instance
    .post(`indoors/comments/${commentId}/claps`)
    .then(success)
    .catch(fail);
}

export {
  commentList,
  createComment,
  updateComment,
  deleteComment,
  clapComment
}