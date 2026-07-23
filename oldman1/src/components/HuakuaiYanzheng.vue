<template>
  <div class="huakuai-box" :class="{ passed: verified }">
    <div class="huakuai-image" :style="{ backgroundImage: `url(${captchaImage})` }">
      <span>{{ verified ? '验证通过' : '拖动滑块到最右侧' }}</span>
    </div>
    <div class="huakuai-track">
      <input
        v-model.number="value"
        type="range"
        min="0"
        max="100"
        :disabled="verified"
        @input="check"
        @change="check"
      />
      <div class="huakuai-progress" :style="{ width: `${verified ? 100 : value}%` }"></div>
      <strong>{{ verified ? '已完成' : '滑动验证' }}</strong>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import { withTupianBanben } from '../utils/tupianBanben'

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false
  },
  resetKey: {
    type: [Number, String],
    default: 0
  }
})

const emit = defineEmits(['update:modelValue'])

const images = [
  '/uploads/captcha/captcha-1.jpg',
  '/uploads/captcha/captcha-2.jpg',
  '/uploads/captcha/captcha-3.jpg'
]

const value = ref(0)
const imageIndex = ref(Math.floor(Math.random() * images.length))
const verified = computed(() => props.modelValue)
const captchaImage = computed(() => withTupianBanben(images[imageIndex.value % images.length]))

function check() {
  if (value.value >= 96) {
    value.value = 100
    emit('update:modelValue', true)
  }
}

function reset() {
  value.value = 0
  imageIndex.value = (imageIndex.value + 1) % images.length
  emit('update:modelValue', false)
}

watch(() => props.resetKey, reset)
</script>

<style scoped>
.huakuai-box {
  display: grid;
  gap: 8px;
}

.huakuai-image {
  min-height: 74px;
  display: grid;
  place-items: center;
  border: 1px solid #d6e1db;
  border-radius: 6px;
  overflow: hidden;
  color: #ffffff;
  background-color: #e9f3ef;
  background-position: center;
  background-size: cover;
}

.huakuai-image span {
  width: 100%;
  min-height: 74px;
  display: grid;
  place-items: center;
  padding: 0 12px;
  background: rgba(15, 61, 58, 0.48);
  font-weight: 700;
}

.huakuai-track {
  position: relative;
  height: 40px;
  border: 1px solid #cbd5e1;
  border-radius: 6px;
  overflow: hidden;
  background: #f8fafc;
}

.huakuai-track input {
  position: absolute;
  inset: 0;
  z-index: 2;
  width: 100%;
  height: 100%;
  margin: 0;
  opacity: 0;
  cursor: grab;
}

.huakuai-track input:disabled {
  cursor: default;
}

.huakuai-progress {
  position: absolute;
  inset: 0 auto 0 0;
  z-index: 1;
  background: linear-gradient(90deg, #d9f3ee, #8dd8ca);
  transition: width 0.18s ease;
}

.huakuai-track strong {
  position: relative;
  z-index: 1;
  height: 100%;
  display: grid;
  place-items: center;
  color: #334155;
  font-size: 13px;
}

.huakuai-box.passed .huakuai-track {
  border-color: #73c7a5;
}
</style>
