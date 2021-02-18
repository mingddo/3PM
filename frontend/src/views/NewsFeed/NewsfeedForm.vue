<template>
  <div>
    <!-- <div class="newsfeed-header">
      <NewsFeedHeader :Category="Number(Category)" />
    </div> -->
    <div class="newsfeed-body">
      <Sidebar />
      <div class="newsfeed newsfeed-form">
        <GroupNav v-if="Category == 2" :isHome="true"/>
        <section class="newsfeed-form-content">
          <div class="newsfeed-form-category"> <span class="highlight" @click="changeCategory">{{ Category_list[Category - 1] }}</span> </div>
          <div v-if="changeCate" class="newsfeed-form-group">
            <div v-for="(cate, idx) of Category_list" :key="idx">
              <button @click="selectCate(idx)">{{ cate }}</button>
            </div>
            <button @click="changeCategory"> 닫기 </button>
          </div>
          <div class="newsfeed-form-profile">
            <NewsFeedProfile  :userId="userpk" :proImg="myImg ? myImg : defaultImg"/>
            <div class="newsfeed-form-profile-name">
              {{myName}}
            </div>
            <div v-if="Category == 2" class="newsfeed-form-group-container">
              <div v-if="!fixGroup" @click="chooseGroup" class="newsfeed-form-group-btn highlight">{{ groupName }}</div>
              <div v-else class="highlight">{{ groupName }}</div>

              <div v-if="select" class="newsfeed-form-group">
                <div class="newsfeed-form-group-list" v-if="groupList.length > 0">
                  <div v-for="(group, idx) of groupList" :key="idx">
                    <button @click="selectGroup(group)">{{ group.name }}</button>
                  </div>
                </div>
                <div class="newsfeed-form-group-none" v-else>
                  가입된 그룹이 없습니다. 그룹에 가입해주세요!
                  <div class="newsfeed-form-group-redirect-btn" @click="goToGroupPage">
                    그룹 찾으러 가기
                  </div>
                </div>
                <div class="newsfeed-form-group-list-btn">
                  <button @click="chooseGroup">닫기</button>
                </div>
              </div>

            </div>
          </div>

          <div class="newsfeed-form-tag-input-container">
            <div class="tag-input-frame">
              <div
                class="searchResultList"
                @keyup.down="selectValue('down')"
                @keyup.up="selectValue('up')"
                @mouseover="removeValue"
              >
                <form @submit.prevent="autoTag" class="search_input">
                  <div class="inputframe">
                    <label for="search"></label>
                    <input
                      class="r"
                      required
                      type="text"
                      id="search"
                      placeholder="태그를 입력해 주세요"
                      v-model.trim="keyword"
                      @input="autoTag"
                      @keyup.enter="addTag(keyword)"
                      autocomplete="off"
                      ref="search"
                    />
                  </div>
                </form>
                <ul
                  class="r"
                  :class="[autotag ? 'autocompleteUl' : 'autocompleteUl active']"
                  tabindex="0"
                >
                  <li
                    class="autoImtem"
                    tabindex="-1"
                    v-for="(tag, idx) in tags"
                    :key="idx"
                    @click="addTag(tag.value)"
                    @keyup.enter="addTag(tag.value)"
                  >
                    {{ tag.value }}
                  </li>
                </ul>
              </div>
                <!-- <Mentionable
                  :keys="['#']"
                  :items="items"
                  offset="10"
                >
                  <input
                    class="newsfeed-form-tag-input"
                    id="tags"
                    type="text"
                    placeholder="태그를 작성후 엔터를 눌러 태그를 등록해주세요"
                    :value="inputTag"
                    @input="autoTag"
                    @click="setInputTag"
                    @keyup.enter="addTag"
                    @keyup.space="addTag"
                    autocomplete="off"
                  />
                </Mentionable> -->
                <i class="far fa-question-circle" @click="qtToggle"></i>
            </div>
            <div :class="[!tagToggle ? 'tag-describe' : 'tag-describe active']">
              태그가 무엇인가요? : 게시물을 표현할 수 있는 짧은 단어를 뜻합니다.
              태그를 입력하시면 검색시 회원님의 게시물이 나타납니다.
            </div>
          </div>
          
          <div class="newsfeed-form-tag-container">
            <span v-for="(tag, idx_t) in form.tags" :key="idx_t">
              <button>
                {{ tag }}
                <i @click="deleteTag(tag)" class="fas fa-times-circle"></i>
              </button>
            </span>
          </div>
          <div class="newsfeed-form-content-input-container">
            <textarea
              v-if="type == 'SHARE'"
              readonly
              class="newsfeed-form-content-input"
              name="content"
              placeholder="요즘 어떤 활동을 하고 계신가요?"
              id="content"
              cols="30"
              rows="10"
              v-model="this.$route.params.feed.content"
            ></textarea>
            <textarea
              v-else
              class="newsfeed-form-content-input"
              name="content"
              placeholder="요즘 어떤 활동을 하고 계신가요?"
              id="content"
              cols="30"
              rows="10"
              v-model="form.content"
            ></textarea>
          </div>

          <div class="newsfeed-form-file">
            <div class="newsfeed-form-file-box">
              <div v-for="view in form.filePaths" :key="view.id">
                <div class="newsfeed-form-img-prevbox">
                  <img
                    class="newsfeed-form-img-prevbox-delbtn"
                    src="https://img.icons8.com/fluent/20/000000/close-window.png"
                    @click="deleteImgmodi(view)"
                  />
                  <img :src="`https://dtbqjjy7vxgz8.cloudfront.net/${view}`" alt="미리보기 이미지" />
                </div>
              </div>
              <div v-for="(view2, idx2) in previewUrl" :key="idx2">
                <div class="newsfeed-form-img-prevbox">
                  <!-- <i class="fas fa-times-circle newsfeed-form-img-prevbox-delbtn"></i> -->
                  
                  <img
                    class="newsfeed-form-img-prevbox-delbtn"
                    src="https://img.icons8.com/fluent/20/000000/close-window.png"
                    @click="deleteImg(view2)"
                  />
                  <!-- <i class="far fa-times-circle  newsfeed-form-img-prevbox-delbtn"></i> -->
                  <img :src="view2" alt="미리보기 이미지" />
                </div>
              </div>
              <div class="newsfeed-form-img-box" v-if="totalLen < 5">
                <label for="image"> <div class="newsfeed-form-img-label"> 사진 </div> <i class="fas fa-plus-circle newsfeed-form-img-label-btn"></i> </label>
                <input
                  class="newsfeed-form-img-input"
                  id="image"
                  type="file"
                  @change="selectFile"
                  accept="image/*"
                />
              </div>
            </div>
            <div class="newsfeed-form-file-hr-box">
              <hr class="newsfeed-form-file-hr">
            </div>
            <div class="newsfeed-form-img-guide">
              <ul>
                <li>
                  회원님의 사진을 공유해주세요
                </li>
                <li>
                  사진은 가로로 찍은 사진을 권장합니다
                </li>
                <li>
                  사진은 최대 5장 이내로 등록해주세요
                </li>
              </ul>
            </div>
          </div>

          <div
            class="newsfeed-form-img-box"
            v-if=" Category == 3 && type == 'NEW' || type == 'MODI'"
          >
            <div class="newsfeed-form-input-map" @click="revealMap">
              <i class="fas fa-map-marker-alt"></i>위치 태그하기
            </div>
          </div>
          
          <div v-if="showMap && Category == 3">
            <inputmap @sendLocation="sendLocation" :latitude="latitude" :longitude="longitude"/>
          </div>
          <div class="newsfeed-form-location" v-if="selectLocation && Category == 3">
            <i class="fas fa-map-marker-alt"></i>
            <b>{{ location.placeName || location.place_name }}</b>
            {{ location.address || location.address_name }}
            <span>을 공유합니다</span>
            <!-- {{ location }} -->
          </div>
          <div class="newsfeed-form-submit-btn">
            <button class="create-feed-btn" @click="createFeedNew">작성하기</button>
          </div>
        </section>
        <div class="fa-3x newsfeed-form-loading" v-if="uploadingImg">
          <i class="fas fa-spinner fa-spin"></i>
          <div>
            이미지 업로드 중입니다
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
import '@/assets/css/mention.css'
// import { Mentionable } from 'vue-mention'
import { getprofileInfo } from '@/api/mypage.js'
import { autocompleteTag} from "@/api/search.js";
import { createIndoors } from '@/api/indoors.js'
import { updateIndoors } from '@/api/indoors.js'
import { uploadIndoorsFile } from '@/api/indoors.js'

import { getprofileGroups } from '@/api/mypage.js'
import { createGroupFeed } from '@/api/group.js'
import { updateGroupFeed } from '@/api/group.js'
import { uploadGroupFile } from '@/api/group.js'

import { createOutdoors } from '@/api/outdoors.js'
import { updateOutdoors } from '@/api/outdoors.js'
import { uploadOutdoorsFile } from '@/api/outdoors.js'

import { createWorker } from '@/api/worker.js'
import { updateWorker } from '@/api/worker.js'
import { uploadWorkerFile } from '@/api/worker.js'

import { mapState } from 'vuex'
import Sidebar from '../../components/Common/Sidebar.vue';
import Inputmap from '../../components/NewsFeed/inputmap.vue';
import GroupNav from '../../components/GroupFeed/GroupNav.vue'
import NewsFeedProfile from '../../components/NewsFeed/Common/NewsFeedProfile.vue'

export default {
  name: 'NewsfeedForm',
  components: {
    // NewsFeedHeader,
    Sidebar,
    Inputmap,
    // Mentionable,
    GroupNav,
    NewsFeedProfile
  },
  data() {
    return {
      tags: [],
      autotag: false,
      keyword: "",
      uploadingImg: false,
      city_code: {
        '서울': 1 ,   
        '경기': 2 ,   
        '광주': 3 ,   
        '대구': 4 ,   
        '대전': 5 ,   
        '부산': 6 ,   
        '울산': 7 ,   
        '인천': 8 ,   
        '강원': 9 ,   
        '경남': 10,   
        '경북': 11,   
        '전남': 12,   
        '전북': 13,   
        '충북': 14,   
        '충남': 15,   
        '제주': 16,   
        '세종': 17   
      },
      tagToggle:false,
      Category: 0,
      type: 'NEW',
      completed: false,
      select: false,
      inputTag: "",
      form: {
        tags: [],
        filePaths: [],
        content: "",
      },
      selectedFile: "",
      imageUrl: ``,
      previewUrl : [],
      fileList: [],
      groupName: "그룹을 선택해주세요",
      groupList: [],
      selectedGroup: null,
      location: {},
      showMap: false,
      fileSelect : false,
      items : [],
      latitude: 36.3553675622378,
      longitude: 127.298408300646,
      on: false,
      abortController: null,
      signal: null,
      temp: [],
      myImg: null,
      myName: null,
      fixGroup: false,
      modiPreview: [],
      selectLocation: false,
      totalLen: null,
      // selectedVideo: null,
      Category_list : ['꽃보다 집', '핵인싸', '청산별곡', '워커홀릭'],
      changeCate: false,
    };
  },
  watch: {
    keyword() {
      this.removeValue();
      if (this.keyword === "") {
        this.autotag = false;
      }
    },
  },
  methods: {
    // 방향키로 태그리스트 이동 및 엔터로 검색창에 반영
    changeValue(str) {
      this.keyword = str;
      this.autotag = false;
      this.tags = [];
      this.removeValue();
      this.$refs.search.focus();
    },
    selectValue(keycode, str) {
      let hasClass = false;
      if (document.querySelector(".r").classList != null) {
        hasClass = document.querySelector(".r").classList.contains("key");
      }

      const isTags = this.tags.length != 0;
      if (keycode === "down" && isTags) {
        if (!hasClass) {
          const thisEl = document.querySelectorAll(".r li")[0];
          document.querySelector(".r").classList.add("key");
          thisEl.classList.add("selected-li");
          thisEl.focus();
        } else {
          const lastEl = document.querySelector(".r li:last-child");
          const thisEl = document.querySelector(".r li.selected-li");
          const nextEl = thisEl.nextElementSibling;
          if (!lastEl.classList.contains("selected-li")) {
            thisEl.classList.remove("selected-li");
            nextEl.classList.add("selected-li");
            nextEl.focus();
          }
        }
      }
      if (keycode === "up" && hasClass) {
        const firstEl = document.querySelectorAll(".r li")[0];
        const thisEl = document.querySelector(".r li.selected-li");
        const prevEl = thisEl.previousElementSibling;
        if (!firstEl.classList.contains("selected-li")) {
          thisEl.classList.remove("selected-li");
          prevEl.classList.add("selected-li");
          prevEl.focus();
        } else {
          this.$refs.search.focus();
        }
      }
      if (keycode === "enter" && hasClass) {
        this.changeValue(str);
      }
    },
    removeValue() {
      if (document.querySelector(".r").classList.contains("key")) {
        document.querySelector(".r").classList.remove("key");
        document
          .querySelector(".r li.selected-li")
          .classList.remove("selected-li");
      }
    },
    autoTag(e) {
      if (e.target.value) {
        this.keyword = e.target.value;
        autocompleteTag(
          this.keyword,
          (res) => {
            if (res.data) {
              this.autotag = true;
              this.tags = res.data;
            }
          },
          (err) => {
            console.error(err);
          }
        ) 
      }
    },
    selectCate (c) {
      this.Category = c + 1
      if (this.Category == 2) {
        getprofileGroups(
          this.userpk,
          (res) => {
            this.groupList = res.data
          },
          (err) => {
            console.log(err)
          }
        )
      }
      this.changeCate = false
    },
    changeCategory () {
      this.changeCate = !this.changeCate;
    },
    goToGroupPage () {
      this.completed = true;
      this.$router.push({ name: 'grouppage' })
    },
    setInputTag () {
      if (this.inputTag == '') {
        this.inputTag = '#'
      }
    },
    sendLocation (place) {
      this.location = place
      this.selectLocation = true;
    },
    revealMap () {
      this.showMap = !this.showMap;
    },
    selectGroup (group) {
      this.groupName = group.name
      this.selectedGroup = group.id
      this.select = false
    },
    chooseGroup () {
      this.select = !this.select
    },
    qtToggle(){
      this.tagToggle = !this.tagToggle
    },

    deleteTag (tag) {
      let check = this.form.tags.findIndex(element => element === tag)
      this.form.tags.splice(check, 1)
    },
    deleteImgmodi (view) {
      let check = this.form.filePaths.findIndex(element => element === view)
      this.form.filePaths.splice(check, 1)
      this.totalLen = this.form.filePaths.length + this.fileList.length
    },
    deleteImg (view) {
      let check = this.previewUrl.findIndex(element => element === view)
      this.previewUrl.splice(check, 1)
      this.fileList.splice(check, 1)
      this.totalLen = this.form.filePaths.length + this.fileList.length
    },
    addTag (tag) {
      if (!tag) {
        this.$swal.fire({
          icon: 'warning',
          text: '태그를 입력해주세요',
          showConfirmButton: false,
          timer: 1500
        })
      } else {
        if (this.form.tags) {
          let check = this.form.tags.findIndex(element => element === tag)
          if (check !== -1) {
            this.$swal.fire({
              icon: 'error',
              text: '이미 존재하는 태그입니다',
              showConfirmButton: false,
              timer: 1500
            })
          } else {
            this.form.tags.push(tag);
            this.removeValue()
            this.keyword = ""
            this.tags = []
            this.autotag = false;
            this.$refs.search.focus();
          }
        }
      }
      this.inputTag = '#';
    },
    getImg () {
      getprofileInfo(
        this.userpk,
        (res) => {
          this.myImg = res.data.user_img
          this.myName = res.data.username
        },
        (err) => {
          console.log(err)
        }
      )
    },
    setDefault () {
      this.Category = this.$route.query.Category
      // console.log('default', this.$route.params.feed)
      if (this.Category == 2) {
        if (this.$route.query.group_id) {
          this.groupName = this.$route.query.group_name,
          this.selectedGroup = this.$route.query.group_id
          this.fixGroup = true;
        } else if (this.$route.params.type == 'MODI') {
          this.groupName = this.$route.params.name,
          this.selectedGroup = this.$route.params.group
          this.fixGroup = true;
        } else {
          getprofileGroups(
            this.userpk,
            (res) => {
              this.groupList = res.data
            },
            (err) => {
              console.log(err)
            }
          )
        }
      }
      if (this.$route.params.type == 'MODI') {
        this.type = 'MODI'
        this.form.content = this.$route.params.feed.content.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n');
        this.form.tags = this.$route.params.feed.tags
        if (this.$route.params.feed.files) {
          this.form.filePaths = this.$route.params.feed.files
          this.totalLen = this.form.filePaths.length
          this.imageUrl = `https://dtbqjjy7vxgz8.cloudfront.net/${this.$route.params.feed.file}`
        }
        if (this.$route.params.feed.lng && this.$route.params.feed.lat) {
          this.latitude = this.$route.params.feed.lat
          this.longitude = this.$route.params.feed.lng
          this.location.placeName = this.$route.params.feed.placeName
          this.location.code = this.$route.params.feed.code
          this.location.lng = this.$route.params.feed.lng
          this.location.lat = this.$route.params.feed.lat
          this.location.address = this.$route.params.feed.address
          this.selectLocation = true;
        }
      } else if (this.$route.params.type == 'SHARE') {
        // let link = document.location.href;
        this.type = 'SHARE'
        if (this.$route.params.feed.file) {
          this.imageUrl = `https://dtbqjjy7vxgz8.cloudfront.net/${this.$route.params.feed.file}`
        }
        this.form.file = this.$route.params.feed.file
        this.form.content = `<b>[공유]</b> <a href="${this.$route.params.link}">원문이동</a> <br> ${this.$route.params.feed.content}`
        this.form.tags = this.$route.params.feed.tags
      }
    },
    selectFile (e) {
      let files = e.target.files || e.dataTransfer.files;
      if (files.length) {
        this.totalLen = this.form.filePaths.length + this.fileList.length
        if (this.totalLen > 5) {
          this.$swal.fire({
            icon: 'error',
            text: '더이상 이미지를 올릴 수 없습니다',
            showConfirmButton: false,
            timer: 1500
          })
        } else {
          this.selectedFile = files[0]
          this.fileList.push(this.selectedFile)
          this.fileSelect = true;
          let reader = new FileReader();
          reader.onload = (e) => {
            this.imageUrl = e.target.result;
            this.previewUrl.push(this.imageUrl)
          }
          reader.readAsDataURL(this.selectedFile);
          this.totalLen = this.form.filePaths.length + this.fileList.length
        }
      }
    },
    imgUpload (id) {
      for (let i of this.fileList) {
        // console.log(i)
        const formData = new FormData ();
        formData.append('file', i)
        if (this.Category == 1) {
          uploadIndoorsFile(
            id,
            formData,
            {},
            (err) => {
              console.log(err)
            }
          )
        } else if (this.Category == 2) {
          uploadGroupFile(
            this.selectedGroup,
            id,
            formData,
            {},
            (err) => {
              console.log(err)
            }
          )
        } else if (this.Category == 3) {
          // 청산별곡
          uploadOutdoorsFile(
            id,
            formData,
            {},
            (err) => {
              console.log(err)
            }
          )
        } else if (this.Category == 4) {
          uploadWorkerFile(
            id,
            formData,
            {},
            (err) => {
              console.log(err)
            }
          )
        }
      }
    },
    createFeedNew () {
      this.completed = true;
      if (this.type == 'NEW' || this.type == 'SHARE') {
        // axios create 요청
        if (this.Category == 1) {
          createIndoors (
            this.form,
            (res) => {
              if (this.fileSelect) {
                this.imgUpload(res.data);
                this.uploadingImg = true;
                setTimeout(() => {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
                }, 1000);
              } else {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              }
            },
            (err) => {
              console.log(err)
              this.$swal.fire({
                icon: 'error',
                text: '인증된 유저만 작성 가능합니다',
                showConfirmButton: false,
                timer: 1500
              })
            }
          )
        } else if (this.Category == 2) {
          if (this.selectedGroup == null) {
            this.$swal.fire({
              icon: 'error',
              text: '그룹을 선택해주세요',
              showConfirmButton: false,
              timer: 1500
            })
          } else {
            // 핵인싸 create 요청
            createGroupFeed(
              this.selectedGroup,
              this.form,
              (res) => {
                // console.log(res)
                if (this.fileSelect) {
                  this.imgUpload(res.data);
                  this.uploadingImg = true;
                  setTimeout(() => {
                    this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category, group: this.selectedGroup } })
                  }, 500);
                } else {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category, group: this.selectedGroup } })
                }
              },
              (err) => {
                console.log(err)
              }

            )
            // formData.append('groupName', this.groupName)
          }
        } else if (this.Category == 3) {
          // 청산별곡 create 요청
          if (this.location) {
            this.form.lat = this.location.lat || this.location.y
            this.form.lng = this.location.lng || this.location.x
            this.form.address = this.location.address || this.location.address_name
            this.form.placeName = this.location.placeName || this.location.place_name
            this.form.code = this.city_code[this.location.city]
          } 
          createOutdoors(
            this.form,
            (res) => {
              if (this.fileSelect) {
                this.imgUpload(res.data);
                this.uploadingImg = true;
                setTimeout(() => {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
                }, 500);
              } else {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              }
            },
            (err) => {
              console.log(err)
            }
          )
        } else if (this.Category == 4) {
          // 워커홀릭 create 요청
          createWorker (
            this.form,
            (res) => {
              if (this.fileSelect) {
                this.imgUpload(res.data);
                this.uploadingImg = true;
                setTimeout(() => {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
                }, 500);
              } else {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              }
            },
            (err) => {
              console.log(err)
              this.$swal.fire({
                icon: 'error',
                text: '다시 로그인해주세요',
                showConfirmButton: false,
                timer: 1500
              })
            }
          )
        } else {
          // 404 페이지 이동
          this.$swal.fire({
            icon: 'error',
            text: '잘못된 접근입니다',
            showConfirmButton: false,
            timer: 1500
          })
        }

      } else {
        // axios put 요청
        if (this.userpk == this.$route.params.feed.user.id) {
          if (this.Category == 1) {
            updateIndoors (
              this.$route.params.feed.id,
              this.form,
              () => {
                if (this.fileSelect) {
                  this.imgUpload(this.$route.params.feed.id);
                  this.uploadingImg = true;
                  setTimeout(() => {
                    this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
                  }, 500);
                } else {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
                }
              },
              (err) => {
                console.log(err)
                this.$swal.fire({
                  icon: 'error',
                  text: '본인만 수정할 수 있습니다',
                  showConfirmButton: false,
                  timer: 1500
                })
              }
            )
          } else if (this.Category == 2) {
            // 핵인싸 put 요청
            updateGroupFeed(
              this.$route.params.group,
              this.$route.params.feed.id,
              this.form,
              () => {
                if (this.fileSelect) {
                  this.imgUpload(this.$route.params.feed.id);
                  this.uploadingImg = true;
                  setTimeout(() => {
                    this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category, group: this.selectedGroup } })
                  }, 500);
                } else {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category, group: this.selectedGroup } })
                }
              },
              (err) => {
                console.log(err)
              }
            )
          } else if (this.Category == 3) {
            // 청산별곡 put 요청
            this.form.lat = this.location.lat || this.location.y
            this.form.lng = this.location.lng || this.location.x
            this.form.address = this.location.address || this.location.address_name
            this.form.placeName = this.location.placeName || this.location.place_name
            this.form.code = this.city_code[this.location.city]
            updateOutdoors(
              this.$route.params.feed.id,
              this.form,
              () => {
                if (this.fileSelect) {
                  this.imgUpload(this.$route.params.feed.id);
                  this.uploadingImg = true;
                  setTimeout(() => {
                    this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
                  }, 500);
                } else {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
                }
              },
              (err) => {
                console.log(err);
                this.$swal.fire({
                  icon: 'error',
                  text: '본인만 수정할 수 있습니다',
                  showConfirmButton: false,
                  timer: 1500
                })
              }
            )
          } else if (this.Category == 4) {
            // 워커홀릭 put 요청
            updateWorker (
              this.$route.params.feed.id,
              this.form,
              () => {
                if (this.fileSelect) {
                  this.imgUpload(this.$route.params.feed.id);
                  this.uploadingImg = true;
                  setTimeout(() => {
                    this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
                  }, 500);
                } else {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
                }
              },
              (err) => {
                console.log(err)
                // alert('본인만 수정할 수 있습니다.')
                this.$swal.fire({
                  icon: 'error',
                  text: '본인만 수정할 수 있습니다',
                  showConfirmButton: false,
                  timer: 1500
                })
              }
            )
          } else {
            // 404 페이지 이동
            // alert('잘못된 접근입니다.')            
            this.$swal.fire({
              icon: 'error',
              text: '잘못된 접근입니다',
              showConfirmButton: false,
              timer: 1500
            })
          }
          
        } else {
          this.$swal.fire({
            icon: 'error',
            text: '본인만 수정할 수 있습니다',
            showConfirmButton: false,
            timer: 1500
          })
        }
      }
    },
  },
  created () {
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.setDefault();
    this.getImg();
    // this.getLocation();
  },
  beforeRouteLeave (to, from, next) {
    if (this.completed) {
      next();
    } else {
      this.$swal.fire({ 
        text: '작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?', 
        icon: 'warning', 
        showCancelButton: true, 
        confirmButtonText: '나가기', 
        cancelButtonText: '돌아가기'
      }).then(result => {
        if (result.isConfirmed) {
          next();
        } else {
          next(false);
        }
      })
    }
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
      defaultImg: (state) => state.defaultImg
    })
  },
};
</script>

<style></style>
