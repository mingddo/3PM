import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();

// get 요청
function getAllGroup(success, fail) {
  instance
    .get('groups')
    .then(success)
    .catch(fail);
}

function getGroupDetail(group_id, success, fail) {
  instance
  .get(`groups/${group_id}`)
  .then(success)
  .catch(fail)
}

function getGroupfeeds(group_id, success, fail) {
  instance
  .get(`groups/${group_id}`)
  .then(success)
  .catch(fail)
}

// post 요청
function createGroup(group, success, fail) {
  instance
  .post(`groups`, group)
  .then(success)
  .catch(fail)
}

function createGroupFeed(group_id, group_feed, success, fail) {
  instance
  .post(`groups/${group_id}/feeds`, group_feed)
  .then(success)
  .catch(fail)
}

function createGroupImg(group_id, group_img, success, fail) {
  instance
  .post(`groups/${group_id}/image`, group_img)
  .then(success)
  .catch(fail)
}

function joinGroup(group_id, success, fail) {
  instance
  .post(`groups/${group_id}/join`)
  .then(success)
  .catch(fail)
}

// PUT 요청
function updateGroupInfo(group_id, group, success, fail) {
  instance
    .put(`groups/${group_id}`, group)
    .then(success)
    .catch(fail);
}

function updateGroupFeed(group_id, group_feed_id, group_feed, success, fail) {
  instance
    .put(`groups/${group_id}/feeds/${group_feed_id}`, group_feed)
    .then(success)
    .catch(fail);
}

// Delete요청
function deleteGroup(group_id, success, fail) {
  instance
  .delete(`groups/${group_id}`)
  .then(success)
  .catch(fail);
}

function secedeGroup(group_id, success, fail) {
  instance
  .delete(`groups/${group_id}/members`)
  .then(success)
  .catch(fail);
}

export{
  getAllGroup, 
  getGroupDetail, 
  getGroupfeeds, 
  createGroup, 
  createGroupFeed, 
  createGroupImg, 
  joinGroup,
  updateGroupInfo,
  updateGroupFeed,
  deleteGroup,
  secedeGroup,
}