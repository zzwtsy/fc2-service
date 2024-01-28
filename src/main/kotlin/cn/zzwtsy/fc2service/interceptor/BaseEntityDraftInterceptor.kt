package cn.zzwtsy.fc2service.interceptor

import cn.zzwtsy.fc2service.model.BaseEntity
import cn.zzwtsy.fc2service.model.BaseEntityDraft
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BaseEntityDraftInterceptor : DraftInterceptor<BaseEntity, BaseEntityDraft> {
    /**
     * Adjust draft before save
     * @param draft The draft can be modified, `id` and `key` properties cannot be changed, otherwise, exception will be raised.
     * @param original The original object null for insert non-null for update, with `id`, `key` and other properties returned by [.dependencies]
     */
    override fun beforeSave(draft: BaseEntityDraft, original: BaseEntity?) {
        if (original == null) {
            draft.createdTime = LocalDateTime.now()
        } else {
            draft.modifiedTime = LocalDateTime.now()
        }
    }
}