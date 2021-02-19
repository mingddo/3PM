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
      <div style="cursor: pointer" v-if="group.leaderId != userpk && group.memberCnt > 1" @click="secede">탈퇴</div>
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
      this.$swal.fire({ 
        text: '정말로 탈퇴하시겠습니까?', 
        icon: 'warning', 
        showCancelButton: true, 
        confirmButtonText: '탈퇴하기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          secedeGroup(
            this.group.id,
            () => {
              // alert('그룹에 탈퇴되었습니다!')
              this.$swal.fire({
                icon: 'success',
                text: '그룹에 탈퇴되었습니다',
                showConfirmButton: false,
                timer: 1500
              })
              this.isjoined = false
            },
            (err) => {
              console.log(err)
            }
          )
        }
      });
    },
    goToGroupDetail () {
      this.$router.push({ name : 'groupdetail', query: { groupId: this.group.id}}).catch((err) => {
        if (err.name === "NavigationDuplicated") {
          location.reload();
        }
      });
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
  width: 100%;
  justify-content: space-between;
  /* border-bottom: 1px solid lightgrey; */
  box-shadow: 0 1px 2px rgb(0 0 0 / 20%);
  padding : 15px;
  background-color: var(--white-color);
  border-radius: 10px;
  margin: auto;
  margin-bottom: 10px;
}
.group-page-group {
  display: flex;
  width: 90%;
  align-items: center;
  /* justify-content: space-between; */
}
.group-page-img-space {
  cursor: pointer;
  width: 60px;
  height: 60px;
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
  min-width: 120px;
  padding : 20px;
}
.group-page-info > div {
  margin: 10px;
}
.group-page-name {
  cursor:pointer;
  font-size: 24px;
}
.group-page-name:hover {
  text-decoration: underline;
  color: #b29887;
}
.group-page-member {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.group-page-created {
  font-size: 12px;
}
@media screen and (max-width: 900px) {
  .group-page-img-space {
    width: 45px;
    height: 45px;
  }
}
</style>
