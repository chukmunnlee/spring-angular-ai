import {HttpClient} from '@angular/common/http';
import { inject, Service } from '@angular/core';
import {ChatResponse} from './models';
import {catchError, map, Observable, of} from 'rxjs';

@Service()
export class ChatService {
  private readonly API = '/api/chat'

  private readonly httpClient = inject(HttpClient)

  sendChatMessage(prompt: string): Observable<ChatResponse> {
    return this.httpClient.post<ChatResponse>(this.API, { prompt })
        .pipe(
          map(resp => ({ ...resp, isBot: true, isError: false })),
          catchError((err: any) => of({
            response: `Chat failed. Error ${JSON.stringify(err)}`,
            isBot: true, isError: true })
          )
        )
  }

}
