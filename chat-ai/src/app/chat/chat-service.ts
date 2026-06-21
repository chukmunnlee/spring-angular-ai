import {HttpClient} from '@angular/common/http';
import { inject, Service } from '@angular/core';
import {ChatRequest, ChatResponse} from './models';
import {catchError, map, Observable, of} from 'rxjs';
import {ulid} from 'ulid';


@Service()
export class ChatService {
  private readonly API = '/api/memory/chat'

  private readonly httpClient = inject(HttpClient)
  private readonly conversationId = ulid()

  sendChatMessage(prompt: string): Observable<ChatResponse> {
    const chatRequest: ChatRequest = { prompt, conversationId: this.conversationId }
    return this.httpClient.post<ChatResponse>(this.API, chatRequest)
        .pipe(
          map(resp => ({ ...resp, isBot: true, isError: false })),
          catchError((err: any) => of({
            response: `Chat failed. Error ${JSON.stringify(err)}`,
            isBot: true, isError: true })
          )
        )
  }

}
