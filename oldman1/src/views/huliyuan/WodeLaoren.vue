<template>
  <section class="zhanwei-page wode-laoren-page">
    <div class="page-heading">
      <p class="xitong-tag">护理员端</p>
      <h2>我的服务老人</h2>
      <p>只展示当前护理员由管理员分配的老人，数据范围由后端接口控制。</p>
    </div>

    <div class="chaxunqu">
      <label><span>老人姓名</span><input v-model.trim="query.laorenXingming" placeholder="请输入老人姓名" /></label>
      <button type="button" @click="load">查询</button>
      <button type="button" class="plain" @click="reset">重置</button>
    </div>

    <div v-if="cuowu" class="form-cuowu">{{ cuowu }}</div>

    <div class="biaoge-wrap">
      <table>
        <thead>
          <tr><th>头像</th><th>编号</th><th>姓名</th><th>性别</th><th>年龄</th><th>床位</th><th>护理级别</th><th>入住状态</th><th>操作</th></tr>
        </thead>
        <tbody>
          <tr v-for="item in list" :key="item.laorenId" class="clickable-row" @click="openDetail(item)">
            <td><img class="avatar-mini" :src="avatarUrl(item.touxiang)" :alt="item.laorenXingming" @error="fallbackAvatar" /></td>
            <td>{{ item.laorenBianhao || '-' }}</td>
            <td>{{ item.laorenXingming }}</td>
            <td>{{ item.xingbie || '-' }}</td>
            <td>{{ item.nianling || '-' }}</td>
            <td>{{ item.chuangwei || '-' }}</td>
            <td>{{ item.jibieMingcheng || '-' }}</td>
            <td>{{ item.ruzhuZhuangtai || '-' }}</td>
            <td><button type="button" @click.stop="openDetail(item)">详情</button></td>
          </tr>
          <tr v-if="!list.length"><td colspan="9" class="kong">暂无服务老人</td></tr>
        </tbody>
      </table>
    </div>

    <div v-if="detailVisible" class="modal-mask">
      <div class="modal-panel">
        <h3>老人详情</h3>
        <div class="detail-head">
          <img :src="avatarUrl(detail.touxiang)" :alt="detail.laorenXingming" @error="fallbackAvatar" />
          <div>
            <strong>{{ detail.laorenXingming }}</strong>
            <span>{{ detail.laorenBianhao || '未编号' }}</span>
          </div>
        </div>
        <dl class="detail-list">
          <div><dt>性别</dt><dd>{{ detail.xingbie || '-' }}</dd></div>
          <div><dt>年龄</dt><dd>{{ detail.nianling || '-' }}</dd></div>
          <div><dt>老人类型</dt><dd>{{ detail.laorenLeixing || '-' }}</dd></div>
          <div><dt>入住状态</dt><dd>{{ detail.ruzhuZhuangtai || '-' }}</dd></div>
          <div><dt>床位信息</dt><dd>{{ detail.chuangwei || '-' }}</dd></div>
          <div><dt>护理级别</dt><dd>{{ detail.jibieMingcheng || '-' }}</dd></div>
          <div><dt>联系电话</dt><dd>{{ detail.lianxiDianhua || '-' }}</dd></div>
          <div><dt>家庭住址</dt><dd>{{ detail.jiatingZhuzhi || '-' }}</dd></div>
          <div class="full"><dt>备注</dt><dd>{{ detail.beizhu || '-' }}</dd></div>
        </dl>
        <div class="modal-actions">
          <button type="button" class="plain" @click="detailVisible = false">关闭</button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getHuliyuanLaorenDetail, getHuliyuanLaorenList } from '../../api/huliyuan'

const avatarFallback = '/uploads/avatar/user-default.jpg'
const query = reactive({ laorenXingming: '' })
const list = ref([])
const detail = ref({})
const detailVisible = ref(false)
const cuowu = ref('')

function avatarUrl(url) {
  return url || avatarFallback
}

function fallbackAvatar(event) {
  if (event.target.src.includes(avatarFallback)) return
  event.target.src = avatarFallback
}

async function load() {
  try {
    cuowu.value = ''
    const res = await getHuliyuanLaorenList(query)
    list.value = res.data || []
  } catch (error) {
    cuowu.value = error.message
  }
}

async function openDetail(item) {
  try {
    cuowu.value = ''
    const res = await getHuliyuanLaorenDetail(item.laorenId)
    detail.value = res.data || item
    detailVisible.value = true
  } catch (error) {
    cuowu.value = error.message || '获取老人详情失败'
  }
}

function reset() {
  query.laorenXingming = ''
  load()
}

onMounted(load)
</script>

<style scoped>
.wode-laoren-page { gap: 18px; }
.clickable-row { cursor: pointer; }
.clickable-row:hover { background: #f8fafc; }
.avatar-mini { width: 48px; height: 48px; border-radius: 10px; object-fit: cover; background: #eef5f1; }
.kong { color: #94a3b8; text-align: center; }
.modal-mask { position: fixed; inset: 0; z-index: 20; display: grid; place-items: center; padding: 20px; background: rgba(15, 23, 42, 0.42); }
.modal-panel { width: min(720px, 100%); display: grid; gap: 16px; border-radius: 8px; padding: 22px; background: #fff; box-shadow: 0 22px 70px rgba(15, 23, 42, 0.24); }
.modal-panel h3 { margin: 0; }
.detail-head { display: flex; align-items: center; gap: 16px; border-bottom: 1px solid #e2e8f0; padding-bottom: 14px; }
.detail-head img { width: 112px; height: 112px; border-radius: 14px; object-fit: cover; background: #eef5f1; }
.detail-head div { display: grid; gap: 8px; }
.detail-head strong { font-size: 22px; color: #1f2937; }
.detail-head span { color: #64748b; }
.detail-list { display: grid; grid-template-columns: repeat(2, minmax(0, 1fr)); gap: 10px 18px; margin: 0; }
.detail-list div { display: grid; gap: 5px; }
.detail-list .full { grid-column: 1 / -1; }
.detail-list dt { color: #64748b; font-size: 13px; }
.detail-list dd { margin: 0; color: #1f2937; line-height: 1.6; word-break: break-word; }
.modal-actions { display: flex; justify-content: flex-end; }
.modal-actions button { min-height: 38px; border-radius: 6px; padding: 0 16px; border: 1px solid #cbd5e1; color: #334155; background: #fff; font-weight: 700; }
@media (max-width: 720px) {
  .detail-list { grid-template-columns: 1fr; }
}
</style>
