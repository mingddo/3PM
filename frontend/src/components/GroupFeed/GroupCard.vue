<template>
  <div class="group-page-card">
    <div class="group-page-group">
      <div class="group-page-img-section">
        <div class="group-page-img-space">
          <img class="group-detail-img" @click="goToGroupDetail" :src="group.groupImg ? `https://dtbqjjy7vxgz8.cloudfront.net/${group.groupImg}` : 'https://picsum.photos/200'" alt="그룹이미지" />
        </div>
      </div>
      <div class="group-page-info">
        <div @click="goToGroupDetail" class="group-page-name">
          {{ group.name }}
        </div>
        <div>
          {{ group.description }}
        </div>
        <div class="group-page-created">
          {{ group.createdDate.split('T')[0]}}
        </div>
      </div>
    </div>
    <div class="group-page-member">
      <i class="fas fa-users">{{ group.memberCnt}}</i>
      <div  v-if="group.leaderId != userpk && group.memberCnt > 1" @click="secede">그룹탈퇴하기</div>
    </div>

  </div>
</template>

<script>
import { mapState } from 'vuex'
import { secedeGroup } from '@/api/group.js'

export default {
  props: {
    group: Object,
  },
  methods: {
    secede () {
      const answer = window.confirm('정말로 탈퇴하시겠습니까?')
      if (answer) {
        secedeGroup(
          this.group.id,
          (res) => {
            console.log(res)
            alert('그룹에 탈퇴되었습니다!')
            this.isjoined = false
          },
          (err) => {
            console.log(err)
          }
        )
      }
    },
    goToGroupDetail () {
      this.$router.push({ name : 'groupdetail', query: { groupId: this.group.id}})
    },
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
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
  justify-content: space-between;
}
.group-page-img-space {
  cursor: pointer;
  width: 80px;
  height: 80px;
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
  /* width: 50%; */
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
.group-page-created {
  font-size: 12px;
}
</style>
