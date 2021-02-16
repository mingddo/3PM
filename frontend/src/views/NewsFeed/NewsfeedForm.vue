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
          <div class="newsfeed-form-profile">
            <div class="newsfeed-form-profile-img-space">
              <img
                src="http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
                alt="유저프로필이미지"
                class="newsfeed-form-profile-img"
              />
            </div>
            <div class="newsfeed-form-profile-name">
              username
            </div>
            <div v-if="Category == 2" class="newsfeed-form-group-container">
              <div @click="chooseGroup">{{ groupName }}</div>
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
                <Mentionable
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
                </Mentionable>
                <i class="far fa-question-circle" @click="qtToggle"></i>
            </div>
            <div :class="[!tagToggle ? 'tag-describe' : 'tag-describe active']">
              태그가 무엇인가요? : 게시물을 표현할 수 있는 짧은 단어를 뜻합니다.
              태그를 입력하시면 검색시 회원님의 게시물이 나타납니다.
            </div>
          </div>

          <div class="newsfeed-form-tag-container">
            <span v-for="(tag, idx) in form.tags" :key="idx">
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

          <div class="newsfeed-form-file-box">
            <div class="newsfeed-form-img-box">
              <label for="image"> <i class="far fa-images"></i> 사진 </label>
              <input
                class="newsfeed-form-img-input"
                id="image"
                type="file"
                @change="selectFile"
                accept="image/*"
              />
            </div>
            <div class="newsfeed-form-img-box">
              <label for="video"> <i class="fas fa-video"></i> 동영상 </label>
              <input
                class="newsfeed-form-img-input"
                id="video"
                type="file"
                @change="selectFile"
                accept="video/*"
              />
            </div>
            <div
              class="newsfeed-form-img-box"
              v-if="Category == 2 || Category == 3"
            >
              <div class="newsfeed-form-input-map" @click="revealMap">
                <i class="fas fa-map"></i> 지도
              </div>
            </div>
          </div>
          <div v-if="showMap">
            <inputmap @sendLocation="sendLocation" :latitude="latitude" :longitude="longitude"/>
            <div v-if="location">
              {{ location }}
              <!-- {{ location }} -->
            </div>
          </div>
          <div class="newsfeed-form-img">
            <div v-for="(view, idx) in previewUrl" :key="idx">
              <div class="newsfeed-form-img-prevbox">
                <!-- <i class="fas fa-times-circle newsfeed-form-img-prevbox-delbtn"></i> -->
                <img
                  class="newsfeed-form-img-prevbox-delbtn"
                  src="https://img.icons8.com/fluent/20/000000/close-window.png"
                  @click="deleteImg(view)"
                />
                <!-- <i class="far fa-times-circle  newsfeed-form-img-prevbox-delbtn"></i> -->
                <img :src="view" alt="미리보기 이미지" />
              </div>
            </div>
            <div class="newsfeed-form-img-prevbox">
              사진
            </div>
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
import { Mentionable } from 'vue-mention'
import { searchAutoTag } from '@/api/feed.js'

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

export default {
  name: 'NewsfeedForm',
  components: {
    // NewsFeedHeader,
    Sidebar,
    Inputmap,
    Mentionable,
    GroupNav
  },
  data() {
    return {
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
      location: null,
      showMap: false,
      fileSelect : false,
      items : [],
      latitude: 36.3553675622378,
      longitude: 127.298408300646,
      on: false,
      abortController: null,
      signal: null,
      temp: [],
    };
  },

  methods: {
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
      console.log('뭐지', place)
      this.location = place
      console.log(this.location)
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
    deleteImg (view) {
      console.log(view)
      let check = this.previewUrl.findIndex(element => element === view)
      this.previewUrl.splice(check, 1)
      this.fileList.splice(check, 1)
      console.log(this.fileList)
    },
    tagApi (tag) {
      searchAutoTag(
        tag,
        (res) => {
          this.items = res.data
          console.log(this.items)
        },
        (err) => {
          console.log(err)
        }
      )
    },
    autoTag (e) {
      console.log(e)
      this.inputTag = e.target.value;
      if (this.inputTag == '') {this.inputTag = '#'}
      let tmpTag = this.inputTag.split('#')[1]
        tmpTag = tmpTag.replace(/ /g , '')
      if (tmpTag) {
        console.log('api요청', tmpTag)
        this.tagApi(tmpTag)
      }
    },
    addTag (t) {
      let tag = t.target.value.split('#')[1]
      if (!tag) {
        alert('태그를 입력해주세요!')
      } else {
        if (this.form.tags) {
          let check = this.form.tags.findIndex(element => element === tag)
          if (check !== -1) {
            alert('이미 존재하는 태그입니다')
          } else {
            this.form.tags.push(tag);
          }
        }
      }
      this.inputTag = '#';
    },
    setDefault () {
      this.Category = this.$route.query.Category
      if (this.Category == 2) {
        getprofileGroups(
          this.userpk,
          (res) => {
            console.log(res)
            this.groupList = res.data
          },
          (err) => {
            console.log(err)
          }
        )
      }
      if (this.$route.params.type == 'MODI') {
        this.type = 'MODI'
        this.form.content = this.$route.params.feed.content.replace(/(<br>|<br\/>|<br \/>)/g, '\r\n');
        this.form.tags = this.$route.params.feed.tags
        if (this.$route.params.feed.file) {
          this.imageUrl = `https://dtbqjjy7vxgz8.cloudfront.net/${this.$route.params.feed.file}`
        }
        if (this.$route.params.feed.longitude && this.$route.params.feed.latitude) {
          this.longitude = this.$route.params.feed.lng
          this.latitude = this.$route.params.feed.lat
          this.location.placeName = this.$route.params.feed.placeName
          this.location.city = this.$route.params.feed.city
          this.location.lng = this.$route.params.feed.lng
          this.location.lat = this.$route.params.feed.lat
          this.location.address = this.$route.params.feed.address
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
        this.fileList.push(files[0])
        this.fileSelect = true;
        this.selectedFile = files[0]
        console.log(this.selectedFile)
        let reader = new FileReader();
        reader.onload = (e) => {
          this.imageUrl = e.target.result;
          this.previewUrl.push(this.imageUrl)
        }
        reader.readAsDataURL(this.selectedFile);
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
              alert('인증된 유저만 작성 가능합니다.')
            }
          )
        } else if (this.Category == 2) {
          if (this.selectedGroup == null) {
            alert('그룹을 선택해주세요!')
          } else {
            // 핵인싸 create 요청
            createGroupFeed(
              this.selectedGroup,
              this.form,
              (res) => {
                // console.log(res)
                if (this.fileSelect) {
                  this.imgUpload(res.data);
                  alert('이미지 업로드 중입니다!')
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
            this.form.lat = this.location.lat
            this.form.lng = this.location.lng
            this.form.address = this.location.address
            this.form.placeName = this.location.placeName
            // this.form.city = this.city_code[this.location.city]
          }
          createOutdoors(
            this.form,
            (res) => {
              console.log(res)
              if (this.fileSelect) {
                this.imgUpload(res.data);
                alert('이미지 업로드 중입니다!')
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
                alert('이미지 업로드 중입니다!')
                setTimeout(() => {
                  this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
                }, 500);
              } else {
                this.$router.push({ name: 'NewsfeedDetail', query: { id : res.data, Category: this.Category } })
              }
            },
            (err) => {
              console.log(err)
              alert('다시 로그인해주세요')
            }
          )
        } else {
          // 404 페이지 이동
          alert('잘못된 접근입니다.')
        }

      } else {
        // axios put 요청
        if (this.userpk == this.$route.params.feed.user.id) {
          if (this.Category == 1) {
            console.log(this.$route.params.feed.id)
            updateIndoors (
              this.$route.params.feed.id,
              this.form,
              (res) => {
                console.log('수정', res)
                this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
              },
              (err) => {
                console.log(err)
                alert('본인만 수정할 수 있습니다.')
              }
            )
          } else if (this.Category == 2) {
            // 핵인싸 put 요청
            updateGroupFeed(
              this.$route.params.group,
              this.$route.params.feed.id,
              this.form,
              (res) => {
                console.log(res)
                this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, group: this.$route.params.group, Category: this.Category } })
              },
              (err) => {
                console.log(err)
              }
            )
          } else if (this.Category == 3) {
            // 청산별곡 put 요청
            updateOutdoors(
              this.$route.params.feed.id,
              this.form,
              (res) => {
                console.log('수정', res)
                this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
              },
              (err) => {
                console.log(err)
                alert('본인만 수정할 수 있습니다.')
              }
            )
          } else if (this.Category == 4) {
            // 워커홀릭 put 요청
            updateWorker (
              this.$route.params.feed.id,
              this.form,
              (res) => {
                console.log('수정', res)
                this.$router.push({ name: 'NewsfeedDetail', query: { id : this.$route.params.feed.id, Category: this.Category } })
              },
              (err) => {
                console.log(err)
                alert('본인만 수정할 수 있습니다.')
              }
            )
          } else {
            // 404 페이지 이동
            alert('잘못된 접근입니다.')
          }
          
        } else {
          alert('본인만 수정할 수 있습니다!!!')
        }
      }
    },
  },
  created () {
    this.setDefault();
    // this.getLocation();
  },
  beforeRouteLeave (to, from, next) {
    if (this.completed) {
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
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style></style>
