import { Injectable } from '@angular/core';
import { InjectableRxStompConfig, RxStompService } from '@stomp/ng2-stompjs';
import { WebSocketService } from './websocketService';

export const progressStompConfig: InjectableRxStompConfig = {
  webSocketFactory: () => {
    return new WebSocket('ws://localhost:9002/ws');
  }
};

@Injectable()
export class RequestWebsocketService extends WebSocketService {
  constructor(stompService: RxStompService) {
    super(
      stompService,
      progressStompConfig
    );
  }
}
