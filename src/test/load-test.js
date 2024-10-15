import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
  vus: 100,
  duration: '1m',
};

export default function () {
  http.get('http://localhost:8080/products'); // escolha um endpoint da aplicação.
  sleep(1); // Tempo de espera, em segundos, entra as requisições.
}
