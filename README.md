# DDD DEMO
[阿里技术专家殷浩详解DDD系列](https://mp.weixin.qq.com/s/1rdnkROdcNw5ro4ct99SqQ)，代码实践

# 一：[Domain Primitive](https://mp.weixin.qq.com/s?__biz=MzAxNDEwNjk5OQ==&mid=2650403892&idx=1&sn=a91fa477392e80f9420a8ca4d26bcace&chksm=83953c2cb4e2b53a6af3b5a82c3b7d7ed932bfe83f59877a935445ae89edd0ff4ee1c4e82fba&scene=178&cur_album_id=1452661944472977409#rd)

### 定义：让我们重新来定义一下 Domain Primitive ：Domain Primitive 是一个在特定领域里，拥有精准定义的、可自我验证的、拥有行为的 Value Object 。

- DP是一个传统意义上的Value Object，拥有Immutable的特性
- DP是一个完整的概念整体，拥有精准定义
- DP使用业务域中的原生语言
- DP可以是业务域的最小组成部分、也可以构建复杂组合

### 使用 Domain Primitive 的三原则

- 让隐性的概念显性化
- 让隐性的上下文显性化
- 封装多对象行为

# 二：[应用架构](https://mp.weixin.qq.com/s?__biz=MzAxNDEwNjk5OQ==&mid=2650404060&idx=1&sn=cacf40d19528f6c2d9fd165151d6e8b4&chksm=83953cc4b4e2b5d2bd4426e0d2103f2e95715b682f3b7ff333dbb123eaa79d3e5ad24f64beac&cur_album_id=1452661944472977409&scene=189#wechat_redirect)

```java
public class TransferController {

    private TransferService transferService;

    public Result<Boolean> transfer(String targetAccountNumber, BigDecimal amount, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return transferService.transfer(userId, targetAccountNumber, amount, "CNY");
    }
}

public class TransferServiceImpl implements TransferService {

    private static final String TOPIC_AUDIT_LOG = "TOPIC_AUDIT_LOG";
    private AccountMapper accountDAO;
    private KafkaTemplate<String, String> kafkaTemplate;
    private YahooForexService yahooForex;

    @Override
    public Result<Boolean> transfer(Long sourceUserId, String targetAccountNumber, BigDecimal targetAmount, String targetCurrency) {
        // 1. 从数据库读取数据，忽略所有校验逻辑如账号是否存在等
        AccountDO sourceAccountDO = accountDAO.selectByUserId(sourceUserId);
        AccountDO targetAccountDO = accountDAO.selectByAccountNumber(targetAccountNumber);

        // 2. 业务参数校验
        if (!targetAccountDO.getCurrency().equals(targetCurrency)) {
            throw new InvalidCurrencyException();
        }

        // 3. 获取外部数据，并且包含一定的业务逻辑
        // exchange rate = 1 source currency = X target currency
        BigDecimal exchangeRate = BigDecimal.ONE;
        if (sourceAccountDO.getCurrency().equals(targetCurrency)) {
            exchangeRate = yahooForex.getExchangeRate(sourceAccountDO.getCurrency(), targetCurrency);
        }
        BigDecimal sourceAmount = targetAmount.divide(exchangeRate, RoundingMode.DOWN);

        // 4. 业务参数校验
        if (sourceAccountDO.getAvailable().compareTo(sourceAmount) < 0) {
            throw new InsufficientFundsException();
        }

        if (sourceAccountDO.getDailyLimit().compareTo(sourceAmount) < 0) {
            throw new DailyLimitExceededException();
        }

        // 5. 计算新值，并且更新字段
        BigDecimal newSource = sourceAccountDO.getAvailable().subtract(sourceAmount);
        BigDecimal newTarget = targetAccountDO.getAvailable().add(targetAmount);
        sourceAccountDO.setAvailable(newSource);
        targetAccountDO.setAvailable(newTarget);

        // 6. 更新到数据库
        accountDAO.update(sourceAccountDO);
        accountDAO.update(targetAccountDO);

        // 7. 发送审计消息
        String message = sourceUserId + "," + targetAccountNumber + "," + targetAmount + "," + targetCurrency;
        kafkaTemplate.send(TOPIC_AUDIT_LOG, message);

        return Result.success(true);
    }

}
```

# 三：[Repository模式](https://mp.weixin.qq.com/s?__biz=MzAxNDEwNjk5OQ==&mid=2650406692&idx=1&sn=4a4ac4168299d8ca1905a4f457ae4c59&chksm=8395373cb4e2be2a2d066a5ea4e631fd6270e969ce61883b488f61c1ce33fbc0b362ec9cbf7b&scene=178&cur_album_id=1452661944472977409#rd)

# 四：[领域层设计规范](https://mp.weixin.qq.com/s?__biz=MzAxNDEwNjk5OQ==&mid=2650414919&idx=1&sn=0ad1df1a1b0e2488f7faa21008fdbdd0&chksm=8396d75fb4e15e49341b07022780dcb8dca66a0efb7f129d4de86a5ef5d8a890f6e0d2fd6432&scene=178&cur_album_id=1452661944472977409#rd)

# 五：[聊聊如何避免写流水账代码](https://mp.weixin.qq.com/s?__biz=MzAxNDEwNjk5OQ==&mid=2650427571&idx=1&sn=bfc3c1c6f189965a1a4c7f3918012405&chksm=839698abb4e111bd5e02344f27d86c928ccfe4d3da1649817b02924c07f681fc1a7ea818f442&scene=178&cur_album_id=1452661944472977409#rd)