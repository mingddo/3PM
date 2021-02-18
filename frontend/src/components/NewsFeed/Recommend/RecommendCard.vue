<template>
  <div class="recommend_card" id="recoContent" :style="`background-image: url(https://dtbqjjy7vxgz8.cloudfront.net/${ op.files && op.files.length > 0 ? op.files[0] : defaultImg}); background-size: auto 100%`">
    <div class="recommend_contentFrame">
      <div v-if="Category == 3">
        <div class="place">
          <span class="place-city"> {{ city_code[op.code] }} </span> <span> {{ op.placeName}} </span> 
        </div>
      </div>
      <div class="recommend_preview">
        <div class="tag">
          <span v-for="(tag,idx) in op.tags" :key="idx">
            {{ tag }}
          </span>
        </div>
        <div class="content_preview">
          <div v-if="Category == 2" @click="goToGroupDetail" style="cursor:pointer; margin-bottom:20px">
            {{ op.groupName }}
          </div>
          <div class="imgFrame">
            <img v-if="op.user" :src="`https://dtbqjjy7vxgz8.cloudfront.net/${op.user.img}`" alt="" />
          </div>
          <div v-if="op.user" class="preview_username" @click="goToProfile">{{ op.user.nickname }}</div>
          <div  @click="goToPage" class="preview_content" v-html="contentBox ? contentBox : op.content">
            <!-- {{ op.content }} -->
          </div>
          <div v-if="Category == 4" style="cursor: pointer;" class="preview_link" @click="goToPage">
            바로이동
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { mapActions } from 'vuex'
export default {
  props: {
    op: Object,
    Category: Number,
  },
  data() {
    return {
      defaultImg : [`꽃보다집_추천.jpg`, `핵인싸_추천.jpg`, `청산별곡_추천.jpg`][this.Category - 1],
      contentBox: this.op.content,
      city_code: {
        1 : '서울',   
        2 : '경기',   
        3 : '광주',   
        4 : '대구',   
        5 : '대전',   
        6 : '부산',   
        7 : '울산',   
        8 : '인천',   
        9 : '강원',   
        10: '경남',   
        11: '경북',   
        12: '전남',   
        13: '전북',   
        14: '충북',   
        15: '충남',   
        16: '제주',   
        17: '세종'   
      },
    };
  },
  created () {
    this.setContentIndent();
    this.setPlace();
  },
  methods : {
    ...mapActions(['setPlaceName']),
    setPlace () {
      this.setPlaceName(this.op.code)
    },
    goToGroupDetail () {
      this.$router.push({
        name: "groupdetail",
        query: { groupId: this.op.groupId},
      });
    },
    goToProfile () {
      this.$router.push({
        name: "MyPage",
        query: { name: this.op.user.id }
      })
    },
    setContentIndent () {
      if (this.op.content) {
        this.contentBox = this.op.content
        this.contentBox = this.contentBox.replace(/(\n|\r\n)/g, '<br>')
      }
    },
    goToPage () {
      if (this.Category == 4) {
        window.open(this.op.link)
      } else if (this.Category == 2) {
        this.$router.push({
          name: "NewsfeedDetail",
          query: { id: this.op.id, group: this.op.groupId, Category: this.op.category },
          params: {op: this.op },
        })
      } else {
        // router push
        this.$router.push({
          name: "NewsfeedDetail",
          query: { id: this.op.id, Category: this.op.category },
          params: { fd: this.op },
        })
      }
    },
  },
};
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.preview_username {
  font-size: 20px;
  font-weight: 700;
  margin-bottom: 10px;
  cursor: pointer;
}

.preview_content {
  font-size: 15px;
  margin: 16px auto;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
  height: 80px;
  width: 90%;
  white-space: normal;
  word-break: break-word;
}

.preview_link {
  cursor: pointer;
}

.preview_link:hover {
  text-decoration: underline;
}

.recommend_card {
  width: 100%;
  height: 300px;
  margin: auto;
  z-index: 100;
  /* background-image: url("https://i.pinimg.com/564x/a1/be/e4/a1bee43eb0516544180573aa8fed7e37.jpg"); */
  background-color: rgb(0, 0, 0, 0.8);;
  background-size: cover;
  background-position: 50% 50%;
  background-repeat: no-repeat;
  border-radius: 5px;
}
.recommend_contentFrame {
  width: auto;
  height: 100%;
  /* margin: 5px; */
  position: relative;
}

.screen {
  position: relative;
  overflow: hidden;
}

.recommend_preview {
  position: absolute;
  width: 100%;
  height: 80px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(57, 58, 58, 0.9);
  bottom: 0%;
  font-size: 14px;
  text-align: center;
  padding: 20px;
  color: rgb(236, 234, 234);
  transition: all 0.35s;
  border-radius: 5px;
  overflow: hidden;
}
.place {
  padding: 10px;
  background-color: rgb(57, 58, 58, 0.9);
  height: 45px;
  color: var(--white-color)
}
.place-city {
  font-size: 24px;
}

.tag {
  width: 200px;
  height: 20px;
  margin: auto;
  font-size: 15px;
  font-weight: 600;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.content_preview {
  position: absolute;
  top: 150%;
  width: 90%;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}
.imgFrame {
  width: 60px;
  height: 60px;
  margin-bottom: 5px;
  object-fit: cover;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 70%;
}

.imgFrame > img {
  width: 80px;
}
.recommend_contentFrame:hover .recommend_preview {
  height: 300px;
}

.recommend_contentFrame:hover .place {
  display: none;
}
.recommend_contentFrame:hover .tag {
  display: none;
}

.recommend_contentFrame:hover .content_preview {
  top: 20%;
}
</style>
