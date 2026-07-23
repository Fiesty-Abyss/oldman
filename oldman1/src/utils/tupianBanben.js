const KEY = 'oldman_image_version'

export function getTupianBanben() {
  return localStorage.getItem(KEY) || '1'
}

export function updateTupianBanben() {
  const value = String(Date.now())
  localStorage.setItem(KEY, value)
  window.dispatchEvent(new CustomEvent('oldman-image-version-change', { detail: value }))
  return value
}

export function withTupianBanben(url, version = getTupianBanben()) {
  if (!url) return ''
  const separator = url.includes('?') ? '&' : '?'
  return `${url}${separator}v=${encodeURIComponent(version)}`
}
