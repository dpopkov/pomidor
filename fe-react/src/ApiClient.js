export default class ApiClient {
  static SERVER_URL = 'http://localhost:8080/pomidor';

  static sendPomidor(description, start, end) {
    return fetch(ApiClient.SERVER_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        description,
        start,
        end,
      }),
    });
  }

  static getAllPomidors() {
    return fetch(ApiClient.SERVER_URL);
  }
}
