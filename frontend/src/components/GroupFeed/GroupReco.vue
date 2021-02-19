<template>
  <div class="group-reco-frame">
    <div class="group-reco-title">
      오늘의 신규 그룹
    </div>
    <div v-if="recoGroup" class="group-reco"> 
      <!-- <button class="group-reco-prev-btn" v-if="prev" @click="prePage">이전</button> -->
      <div class="group-reco-prev-btn" v-if="prev" @click="prePage"><i class="fas fa-chevron-circle-left"></i></div>
        <div v-if="recoGroup[page]" class="group-reco-card" :style="`background-image: url(https://dtbqjjy7vxgz8.cloudfront.net/${recoGroup[page].groupImg ? recoGroup[page].groupImg : defaultImg}) backgroundSize: 'cover'`">
          <div class="group-img-section">
            <div class="group-reco-img-space">
              <img class="group-reco-img" :src="recoGroup[page].groupImg ? `https://dtbqjjy7vxgz8.cloudfront.net/${recoGroup[page].groupImg}` : `https://dtbqjjy7vxgz8.cloudfront.net/${defaultImg}`" alt="그룹프로필">
            </div>
          </div>
          <div class="group-content-section">
            <div class="group-reco-name" @click="goToGroupDetail">
              {{ recoGroup[page].name }}
            </div>
            
            <div class="group-reco-description">
              {{ recoGroup[page].description }}
            </div>
            <div class="group-reco-created">
              개설 : {{ recoGroup[page].createdDate.split('T')[0]}}
            </div>     
            <div class="group-reco-member">
              <i class="fas fa-users"></i>
              {{ recoGroup[page].memberCnt }}
            </div>
          </div>
        </div>

      <!-- <button class="group-reco-next-btn" v-if="next" @click="nextPage">이후</button> -->
      <div class="group-reco-next-btn" v-if="next" @click="nextPage"><i class="fas fa-chevron-circle-right"></i></div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'GroupReco',
  props: {
    recoGroup: Array,
  },
  data() {
    return {
      page: 0,
      prev: false,
      next: true
    };
  },
  mounted() {
    
  },
  methods: {
    prePage () {
      this.next = true;
      this.page -= 1;
      if (this.page == 0) {
        this.prev = false
      } else {
        this.prev = true
      }
    },
    nextPage () {
      this.prev = true;
      this.page += 1;
      if (this.page > this.recoGroup.length - 2) {
        this.next = false
      } else {
        this.next = true;
      }
    },
    goToGroupDetail () {
      this.$router.push({ name : 'groupdetail', query: { groupId: this.recoGroup[this.page].id}})
    },
  },
  computed: {
    ...mapState({
      defaultImg: (state) => state.defaultImg,
    }),
  },
};
</script>

<style>
.group-reco-frame {
  margin-bottom: 30px;
}
.group-reco-title {
  margin: 20px;
  font-size: 24px;
}
.group-reco {
  display: flex;
  position: relative;
  justify-content: space-evenly;
  align-items: center;
  width: 100%;
  height: 180px;
}
.group-reco-card {
  display: flex;
  align-items: center;
  width: 80%;
  height: 100%;
  border-radius: 5px;
  background-color: var(--light-brown);
}
.group-reco-prev-btn {
  cursor: pointer;
  margin: 0 10px;
  position: absolute;
  left: 0;
  z-index: 100;
  font-size: 20px;
}
.group-reco-next-btn {
  position: absolute;
  cursor: pointer;
  margin: 0 10px;
  right: 0;
  z-index: 100;
  font-size: 20px;
}
.group-img-section {
  width: 30%;
  display: flex;
  justify-content: center;
}
.group-content-section {
  padding :10px;
  width: 70%;
}
.group-reco-img-space {
  cursor: pointer;
  width: 80px;
  height: 80px;
  text-align: left;
  /* border: solid 1px rgba(0, 0, 0, 0.2); */
  border-radius: 100%;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5px;
}
.group-reco-img {
  height: 100%;
}
.group-reco-name {
  font-weight: bold;
}
.group-reco-name:hover {
  text-decoration: underline;
  cursor: pointer;
  color: #b29887;
}
.group-reco-description {
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  /* white-space: nowrap; */
  word-wrap: break-word;
  margin: 10px 0;
}
.group-reco-member {
  font-size: 12px;
  text-align: right;
}
.group-reco-created {
  /* position: absolute; */
  /* bottom: 0; 
  right: 0; */
  font-size: 11px;
}
/* @media screen and (max-width: 768px) {
  .group-reco-img-space {
    cursor: pointer;
  width: 50px;
  height: 50px;
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
} */
</style>