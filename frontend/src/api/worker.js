import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

// crud

// c
function createWorker(feed, success, fail) {
  instance
    .post(`worker`, feed)
    .then(success)
    .catch(fail);
}

function uploadWorkerFile(id, file, success, fail) {
  instance
    .post(`worker/${id}`, file)
    .then(success)
    .catch(fail);
}

// r

function reedWorker(feed_pk, success, fail) {
  instance
    .get(`worker/${feed_pk}`)
    .then(success)
    .catch(fail)
}

function workerList(pk, success, fail) {
  instance
    .get(`worker`, {
      params: {
        startNum: pk
      }
    })
    .then(success)
    .catch(fail)
}

// u
function updateWorker(feed_pk, feed, success, fail) {
  instance
    .put(`worker/${feed_pk}`, feed)
    .then(success)
    .catch(fail);
}

// d
function deleteWorker(feed_pk, success, fail) {
  instance
    .delete(`worker/${feed_pk}`)
    .then(success)
    .catch(fail);
}

// 박슈
function clapWorker(pk, success, fail) {
  instance
    .post(`worker/${pk}/claps`)
    .then(success)
    .catch(fail);
}

function clapWorkerList(pk, success, fail) {
  instance
    .get(`worker/${pk}/claps`)
    .then(success)
    .catch(fail);
} 

export {
  createWorker,
  uploadWorkerFile,
  reedWorker,
  workerList,
  updateWorker,
  deleteWorker,
  clapWorker,
  clapWorkerList
}