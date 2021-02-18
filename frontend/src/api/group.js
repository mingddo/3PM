import { AuthorizationInstance } from './index.js'

const instance = AuthorizationInstance();


// get 요청
function groupList(id, success, fail) {
  instance
    .get('groups/feeds', {
      params: {
        startNum: id
      }}
    )
    .then(success)
    .catch(fail)
}

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

function getGroupfeeds(group_id, pk, success, fail) {
  instance
  .get(`groups/${group_id}/feeds`, {
    params: {
      startNum: pk,
    }
  })
  .then(success)
  .catch(fail)
}

function getGroupfeedsDetail(group_id, feed_id, success, fail) {
  instance
    .get(`groups/${group_id}/feeds/${feed_id}`)
    .then(success)
    .catch(fail)
}

function getIsGroupMember(id, success, fail) {
  instance
    .get(`groups/feeds/${id}`)
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

function uploadGroupFile(group_id, feed_id, file, success, fail) {
  instance
    .post(`groups/${group_id}/feeds/${feed_id}`, file)
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

function deleteGroupFeed(group_id, feed_id, success, fail) {
  instance
    .delete(`groups/${group_id}/feeds/${feed_id}`)
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
  groupList,
  getAllGroup, 
  getGroupDetail, 
  getGroupfeeds, 
  getGroupfeedsDetail,
  getIsGroupMember,
  createGroup, 
  createGroupFeed, 
  uploadGroupFile,
  createGroupImg, 
  joinGroup,
  updateGroupInfo,
  updateGroupFeed,
  deleteGroup,
  deleteGroupFeed,
  secedeGroup,
}