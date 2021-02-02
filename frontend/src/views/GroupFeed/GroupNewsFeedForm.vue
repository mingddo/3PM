<template>
  <div>
    <div v-if="!select">
      그룹 선택창
      <div v-for="(group, idx) of groupNames" :key="idx">
        <button @click="selectGroup(group)"> {{ group }} </button>
      </div>
    </div>
    <section v-else>
      <h2>피드 작성하기</h2>
      <div @click="changeGroup">
        {{ form.groupName }} 
      </div>
      <div>
        <label for="tags">태그</label>
        <input class="newsfeed-form-tag-input" id="tags" type="text" placeholder="태그를 입력해주세요" v-model="inputTag" @keyup.enter="addTag">
      </div>
      <div>
        <span v-for="(tag, idx) in form.tags" :key="idx">
          <button>{{ tag }} <i @click="deleteTag(tag)" class="fas fa-times-circle"></i> </button>
        </span>
      </div>
      <div class="newsfeed-form-file-box">
        <div class="newsfeed-form-img-box">
          <label for="image"> <i class="far fa-images"></i> 사진 </label>
          <input class="newsfeed-form-img-input" id="image" type="file" @change="selectFile" accept="image/*">
        </div>
        <div class="newsfeed-form-img-box">
          <label for="video"> <i class="fas fa-video"></i> 동영상 </label>
          <input class="newsfeed-form-img-input" id="video" type="file" @change="selectFile" accept="video/*">
        </div>
      </div> 
      <div>
        <label for="content">내용</label>
        <textarea class="newsfeed-form-content-input" name="content" placeholder="내용을 입력해주세요" id="content" cols="30" rows="10" v-model="form.content"></textarea>
      </div>
      <div class="newsfeed-form-submit-btn">
        <button @click="createFeed">작성하기</button>
      </div>
    </section>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'groupnewsfeedform',
  data() {
    return {
      type: 'NEW',
      select : false,
      groupNames: [ 'Test1', 'Test2', 'Test3'],
      inputTag: "",
      form: {
        tags: [],
        userId: null,
        file: null,
        content: null,
        groupName: null,
      },
    };
  },
  methods: {
    async createFeed () {
      this.completed = true;
      if (this.userpk) {
        this.form.userId = this.userpk
        if (this.type == 'NEW' || this.type == 'SHARE') {
          // axios create 요청
          alert('새 글 작성하는 것!')
        } else {
          // axios put 요청
          alert('수정하는 것')
        }
      } else {
        alert('로그인된 유저만 글을 작성할 수 있습니다.')
      }
    },
    changeGroup () {
      this.select = false;
    },
    selectGroup (name) {
      this.select = true;
      this.form.groupName = name
    },
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files
      // console.log(e)
      if (!files.length)
        this.file = files[0].name
    },
    addTag () {
      if (this.form.tags) {
        let check = this.form.tags.findIndex(element => element === this.inputTag)
        if (check !== -1) {
          alert('이미 존재하는 태그입니다') 
        } else {
          this.form.tags.push(this.inputTag);
        }
      }
      this.inputTag = '';
    },
    setDefault () {
      if (this.$route.params.type == 'MODI') {
        this.type = 'MODI'
        this.form.content = this.$route.params.feed.content
        this.form.tags = this.$route.params.feed.tag
      } else if (this.$route.params.type == 'SHARE') {
        // let link = document.location.href; 
        this.type = 'SHARE'
        this.form.content = `<b>[공유]</b> <br> <a href="${this.$route.params.link}">원문이동</a> <br> ${this.$route.params.feed.content}`
        this.form.tags = this.$route.params.feed.tag
      }
    },
  },
  computed : {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
  created () {
    this.setDefault();
  },
  beforeRouteLeave (to, from, next) {
    if (this.completed || !this.select) {
      next();
    } else {
      const answer = 
        window.confirm('작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?')
      if (answer) {
        next();
      } else {
        next(false);
      }
    }
  },
};
</script>

<style lang="scss" scoped>

</style>