<template>
  <div class="group-page-card">
    <div class="group-page-group">
      <div class="group-page-img-space">
        <img class="group-detail-img" @click="goToGroupDetail" :src="group.groupImg ? `https://dtbqjjy7vxgz8.cloudfront.net/${group.groupImg}` : 'https://picsum.photos/200'" alt="그룹이미지" />
      </div>
      <div class="group-page-info">
        <div @click="goToGroupDetail" class="group-page-name">
          {{ group.name }}
        </div>
        <div>
          {{ group.description }}
        </div>
      </div>
    </div>
    <div class="group-page-member">
      <div>그룹탈퇴하기</div>
      <i class="fas fa-users">{{ group.memberCnt}}</i>
    </div>

  </div>
</template>

<script>
export default {
  props: {
    group: Object,
  },
  methods: {
    goToGroupDetail () {
      this.$router.push({ name : 'groupdetail', query: { groupId: this.group.id}})
    },
  },
};
</script>

<style>
.group-page-card {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid lightgrey;
  padding : 20px;
}
.group-page-group {
  display: flex;
  align-items: center;
}
.group-page-img-space {
  cursor: pointer;
  width: 100px;
  height: 100px;
  text-align: left;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 100%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5px;
}
.group-page-info {
  padding : 20px;
}
.group-page-name {
  cursor:pointer;
  font-size: 24px;
}
.group-page-name:hover {
  text-decoration: underline;
  color: lightgray
}
.group-page-member {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
</style>
