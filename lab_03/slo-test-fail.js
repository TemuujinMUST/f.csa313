import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 20, duration: '1m',
  thresholds: {
    'http_req_duration{name:cart}': ['p(95)<20'], // Performance SLO
    'http_req_failed{name:pay}': ['rate<0.08'], // Reliability SLO
    'checks': ['rate>0.90'], // Availability SLO
    'http_req_duration{name:report}': ['p(95)<100'], // Санаатай хэт хатуу: сервер 200мс-ээс хурдан хариулдаггүй тул энэ FAIL болно
  }
}

export default function () {
  const base = 'http://localhost:3000';
  const c = http.post(`${base}/cart/add`, null, { tags: { name: 'cart' } });
  const r = http.get(`${base}/report`, { tags: { name: 'report' } });
  const p = http.post(`${base}/pay`, null, { tags: { name: 'pay' } });
  check(c, { 'cart 200': (x) => x.status === 200 });
  check(r, { 'report 200': (x) => x.status === 200 });
  check(p, { 'pay 200': (x) => x.status === 200 });
  sleep(1);
}
